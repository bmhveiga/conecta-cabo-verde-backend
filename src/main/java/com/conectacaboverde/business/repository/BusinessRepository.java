package com.conectacaboverde.business.repository;

import com.conectacaboverde.business.entity.Business;
import com.conectacaboverde.business.entity.BusinessStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    List<Business> findByOwnerId(Long ownerId);
    List<Business> findByStatus(BusinessStatus status);
}


