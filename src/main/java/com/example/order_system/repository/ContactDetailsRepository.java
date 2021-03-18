package com.example.order_system.repository;

import com.example.order_system.domain.ContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContactDetailsRepository extends JpaRepository<ContactDetails, Long> {

    List<ContactDetails> findAllByRestaurantId(Long restaurantId);
}
