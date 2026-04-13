package com.conectacaboverde.business.service;

import com.conectacaboverde.business.dto.BusinessRequestDTO;
import com.conectacaboverde.business.dto.BusinessResponseDTO;
import com.conectacaboverde.business.entity.Business;
import com.conectacaboverde.business.entity.BusinessStatus;
import com.conectacaboverde.business.repository.BusinessRepository;
import com.conectacaboverde.exception.BusinessNotFoundException;
import com.conectacaboverde.exception.UnauthorizedBusinessAccessException;
import com.conectacaboverde.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessService {

    private final BusinessRepository businessRepository;

    public BusinessService(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    public BusinessResponseDTO createBusiness(BusinessRequestDTO requestDTO, User owner) {
        Business business = new Business();

        business.setName(requestDTO.getName());
        business.setDescription(requestDTO.getDescription());
        business.setPhone(requestDTO.getPhone());
        business.setEmail(requestDTO.getEmail());
        business.setAddress(requestDTO.getAddress());

        business.setStatus(BusinessStatus.PENDING);
        business.setOwner(owner);

        Business savedBusiness = businessRepository.save(business);

        BusinessResponseDTO responseDTO = new BusinessResponseDTO();
        responseDTO.setId(savedBusiness.getId());
        responseDTO.setName(savedBusiness.getName());
        responseDTO.setDescription(savedBusiness.getDescription());
        responseDTO.setPhone(savedBusiness.getPhone());
        responseDTO.setEmail(savedBusiness.getEmail());
        responseDTO.setAddress(savedBusiness.getAddress());
        responseDTO.setStatus(savedBusiness.getStatus());
        responseDTO.setOwnerId(savedBusiness.getOwner().getId());
        responseDTO.setCreatedAt(savedBusiness.getCreatedAt());
        responseDTO.setUpdatedAt(savedBusiness.getUpdatedAt());

        return responseDTO;
    }

    public List<BusinessResponseDTO> getMyBusinesses(Long ownerId) {
        return businessRepository.findByOwnerId(ownerId).stream().map(business -> {
            BusinessResponseDTO responseDTO = new BusinessResponseDTO();
            responseDTO.setId(business.getId());
            responseDTO.setName(business.getName());
            responseDTO.setDescription(business.getDescription());
            responseDTO.setPhone(business.getPhone());
            responseDTO.setEmail(business.getEmail());
            responseDTO.setAddress(business.getAddress());
            responseDTO.setStatus(business.getStatus());
            responseDTO.setOwnerId(business.getOwner().getId());
            responseDTO.setCreatedAt(business.getCreatedAt());
            responseDTO.setUpdatedAt(business.getUpdatedAt());
            return responseDTO;
        }).toList();
    }

    public List<BusinessResponseDTO> getApprovedBusinesses(){
        return businessRepository.findByStatus(BusinessStatus.APPROVED)
                .stream()
                .map(business -> {
                    BusinessResponseDTO responseDTO = new BusinessResponseDTO();
                    responseDTO.setId(business.getId());
                    responseDTO.setName(business.getName());
                    responseDTO.setDescription(business.getDescription());
                    responseDTO.setPhone(business.getPhone());
                    responseDTO.setEmail(business.getEmail());
                    responseDTO.setAddress(business.getAddress());
                    responseDTO.setStatus(business.getStatus());
                    responseDTO.setOwnerId(business.getOwner().getId());
                    responseDTO.setCreatedAt(business.getCreatedAt());
                    responseDTO.setUpdatedAt(business.getUpdatedAt());
                    return responseDTO;
                })
                .toList();
    }

    public BusinessResponseDTO approveBusiness(Long businessId) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new BusinessNotFoundException("Business not found"));

        business.setStatus(BusinessStatus.APPROVED);

        Business savedBusiness = businessRepository.save(business);

        BusinessResponseDTO responseDTO = new BusinessResponseDTO();
        responseDTO.setId(savedBusiness.getId());
        responseDTO.setName(savedBusiness.getName());
        responseDTO.setDescription(savedBusiness.getDescription());
        responseDTO.setPhone(savedBusiness.getPhone());
        responseDTO.setEmail(savedBusiness.getEmail());
        responseDTO.setAddress(savedBusiness.getAddress());
        responseDTO.setStatus(savedBusiness.getStatus());
        responseDTO.setOwnerId(savedBusiness.getOwner().getId());
        responseDTO.setCreatedAt(savedBusiness.getCreatedAt());
        responseDTO.setUpdatedAt(savedBusiness.getUpdatedAt());

        return responseDTO;
    }

    public BusinessResponseDTO rejectBusiness(Long businessId) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new BusinessNotFoundException("Business not found"));

        business.setStatus(BusinessStatus.REJECTED);

        Business savedBusiness = businessRepository.save(business);

        BusinessResponseDTO responseDTO = new BusinessResponseDTO();
        responseDTO.setId(savedBusiness.getId());
        responseDTO.setName(savedBusiness.getName());
        responseDTO.setDescription(savedBusiness.getDescription());
        responseDTO.setPhone(savedBusiness.getPhone());
        responseDTO.setEmail(savedBusiness.getEmail());
        responseDTO.setAddress(savedBusiness.getAddress());
        responseDTO.setStatus(savedBusiness.getStatus());
        responseDTO.setOwnerId(savedBusiness.getOwner().getId());
        responseDTO.setCreatedAt(savedBusiness.getCreatedAt());
        responseDTO.setUpdatedAt(savedBusiness.getUpdatedAt());

        return responseDTO;
    }

    public BusinessResponseDTO updateBusiness(Long businessId, BusinessRequestDTO requestDTO, User currentUser) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new BusinessNotFoundException("Business not found"));

        if (!business.getOwner().getId().equals(currentUser.getId())) {
            throw new UnauthorizedBusinessAccessException("You are not allowed to access this business");
        }

        business.setName(requestDTO.getName());
        business.setDescription(requestDTO.getDescription());
        business.setPhone(requestDTO.getPhone());
        business.setEmail(requestDTO.getEmail());
        business.setAddress(requestDTO.getAddress());

        Business savedBusiness = businessRepository.save(business);

        BusinessResponseDTO responseDTO = new BusinessResponseDTO();
        responseDTO.setId(savedBusiness.getId());
        responseDTO.setName(savedBusiness.getName());
        responseDTO.setDescription(savedBusiness.getDescription());
        responseDTO.setPhone(savedBusiness.getPhone());
        responseDTO.setEmail(savedBusiness.getEmail());
        responseDTO.setAddress(savedBusiness.getAddress());
        responseDTO.setStatus(savedBusiness.getStatus());
        responseDTO.setOwnerId(savedBusiness.getOwner().getId());
        responseDTO.setCreatedAt(savedBusiness.getCreatedAt());
        responseDTO.setUpdatedAt(savedBusiness.getUpdatedAt());

        return responseDTO;
    }

    public void deleteBusiness(Long businessId, User currentUser) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new BusinessNotFoundException("Business not found"));

        if (!business.getOwner().getId().equals(currentUser.getId())) {
            throw new UnauthorizedBusinessAccessException("You are not allowed to access this business");
        }

        businessRepository.delete(business);
    }
}
