package com.example.order_system.service;

import com.example.order_system.domain.ContactDetails;
import com.example.order_system.domain.Meal;
import com.example.order_system.repository.ContactDetailsRepository;
import com.example.order_system.repository.MealRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealService {

    private final MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public Meal save(Meal meal){
        return mealRepository.save(meal);
    }

    public List<Meal> findAllByRestaurantId(Long restaurantId){
        return mealRepository.findAllByRestaurantId(restaurantId);
    }

    public Optional<Meal> findById(Long id) {
        return mealRepository.findById(id);
    }

    public void deleteById(Long id) {
        mealRepository.deleteById(id);
    }

    public Optional<Meal> findByIdAndRestaurantId(Long id, Long restaurantId) {
        return mealRepository.findByIdAndRestaurantId(id, restaurantId);
    }

    public boolean existsById(Long id) {
        return mealRepository.existsById(id);
    }



}
