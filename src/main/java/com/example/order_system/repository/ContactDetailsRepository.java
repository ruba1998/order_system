package com.example.order_system.repository;

import com.example.order_system.domain.ContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactDetailsRepository extends JpaRepository<ContactDetails, Long> {
}
