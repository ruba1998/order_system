package com.example.order_system.service;

import com.example.order_system.domain.Restaurant;
import com.example.order_system.exceptionHandler.BadRequestException;
import com.example.order_system.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
     }

    public void deleteById(Long id) {
        restaurantRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return restaurantRepository.existsById(id);
    }

    public Optional<Restaurant> findById(Long id) {
        return restaurantRepository.findById(id);
    }

    public List<Restaurant> findAll(){
        return restaurantRepository.findAll();
    }


}
