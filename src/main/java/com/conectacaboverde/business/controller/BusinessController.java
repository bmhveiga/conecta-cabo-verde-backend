package com.conectacaboverde.business.controller;

import com.conectacaboverde.business.dto.BusinessRequestDTO;
import com.conectacaboverde.business.dto.BusinessResponseDTO;
import com.conectacaboverde.business.service.BusinessService;
import com.conectacaboverde.user.entity.User;
import com.conectacaboverde.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/businesses")
public class BusinessController {

    private final BusinessService businessService;
    private final UserRepository userRepository;

    public BusinessController(BusinessService businessService, UserRepository userRepository) {
        this.businessService = businessService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public BusinessResponseDTO createBusiness(@Valid @RequestBody BusinessRequestDTO requestDTO,
                                              Authentication authentication) {

        String email = authentication.getName();

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));

        return businessService.createBusiness(requestDTO, owner);
    }

    @GetMapping("/my")
    public List<BusinessResponseDTO> getMyBusinesses(Authentication authentication) {

        String email = authentication.getName();

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));

        return businessService.getMyBusinesses(owner.getId());
    }

    @GetMapping
    public List<BusinessResponseDTO> getApprovedBusinesses() {
        return businessService.getApprovedBusinesses();
    }

    @PutMapping("/{id}/approve")
    public BusinessResponseDTO approveBusiness(@PathVariable Long id) {
        return businessService.approveBusiness(id);
    }

    @PutMapping("/{id}/reject")
    public BusinessResponseDTO rejectBusiness(@PathVariable Long id) {
        return businessService.rejectBusiness(id);
    }

    @PutMapping("/{id}")
    public BusinessResponseDTO updateBusiness(@PathVariable Long id,
                                              @Valid @RequestBody BusinessRequestDTO requestDTO,
                                              Authentication authentication) {

        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));

        return businessService.updateBusiness(id, requestDTO, currentUser);
    }

    @DeleteMapping("/{id}")
    public String deleteBusiness(@PathVariable Long id, Authentication authentication) {

        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));

        businessService.deleteBusiness(id, currentUser);

        return "Business deleted successfully";
    }
}
