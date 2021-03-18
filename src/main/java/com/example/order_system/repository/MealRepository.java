package com.example.order_system.repository;

import com.example.order_system.domain.ContactDetails;
import com.example.order_system.domain.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MealRepository extends JpaRepository<Meal, Long> {

    List<Meal> findAllByRestaurantId(Long restaurantId);

    Optional<Meal> findByIdAndRestaurantId(Long id, Long restaurantId);
}
