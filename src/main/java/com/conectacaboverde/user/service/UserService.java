package com.conectacaboverde.user.service;

import com.conectacaboverde.exception.EmailAlreadyExistsException;
import com.conectacaboverde.exception.InvalidCredentialsException;
import com.conectacaboverde.security.jwt.JwtService;
import com.conectacaboverde.user.dto.LoginRequestDTO;
import com.conectacaboverde.user.dto.LoginResponseDTO;
import com.conectacaboverde.user.dto.UserRequestDTO;
import com.conectacaboverde.user.dto.UserResponseDTO;
import com.conectacaboverde.user.entity.User;
import com.conectacaboverde.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // Constructor injection
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserResponseDTO createUser(UserRequestDTO requestDTO){

        Optional<User> existingUser = userRepository.findByEmail(requestDTO.getEmail());

        if (existingUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        User user = new User();
        user.setFullName(requestDTO.getFullName());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user.setRole(requestDTO.getRole());
        User savedUser = userRepository.save(user);
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(savedUser.getId());
        responseDTO.setFullName(savedUser.getFullName());
        responseDTO.setEmail(savedUser.getEmail());
        responseDTO.setRole(savedUser.getRole());
        responseDTO.setCreatedAt(savedUser.getCreatedAt());
        responseDTO.setUpdatedAt(savedUser.getUpdatedAt());

        return responseDTO;
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream().map(user -> {
            UserResponseDTO dto = new UserResponseDTO();
            dto.setId(user.getId());
            dto.setFullName(user.getFullName());
            dto.setEmail(user.getEmail());
            dto.setRole(user.getRole());
            dto.setCreatedAt(user.getCreatedAt());
            dto.setUpdatedAt(user.getUpdatedAt());
            return dto;
        }).toList();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public LoginResponseDTO login(LoginRequestDTO requestDTO) {

        Optional<User> existingUser = userRepository.findByEmail(requestDTO.getEmail());

        if (existingUser.isEmpty()) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        User user = existingUser.get();

        boolean passwordMatches = passwordEncoder.matches(
                requestDTO.getPassword(),
                user.getPassword()
        );

        if (!passwordMatches) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());

        return new LoginResponseDTO(token);
    }
}