package com.example.order_system.repository;

import com.example.order_system.domain.ContactDetails;
import com.example.order_system.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
