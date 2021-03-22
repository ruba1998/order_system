package com.example.order_system.controller;

import com.example.order_system.domain.Restaurant;
import com.example.order_system.exceptionHandler.BadRequestException;
import com.example.order_system.exceptionHandler.InternalServerErrorException;
import com.example.order_system.exceptionHandler.ResourceNotFoundException;
import com.example.order_system.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This is a controller to take the requests which related to the restaurant model
 * requests can be: create a new restaurant, edit specific restaurant information, delete specific restaurant,
 * show a specific restaurant information and get a list contains all restaurants
 */


@RestController
public class RestaurantController {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);

    private RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.findAll();
        logger.info("All restaurants fetched");
        return new ResponseEntity(restaurants, HttpStatus.OK);
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant findRestaurantById(@PathVariable Long id) {
        Optional<Restaurant> optionalRestaurant = restaurantService.findById(id);
        return optionalRestaurant.get();
    }

    @DeleteMapping("/restaurants/{id}")
    public ResponseEntity<String> deleteRestaurantById(@PathVariable("id") Long id) {
        if(restaurantService.existsById(id) == false){
            logger.info("Error occurred because this restaurant is not found!");
            throw new ResourceNotFoundException("There is no restaurant with id "+ id);
        }
/*
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Restaurant restaurant = restaurantService.findById(id).get();
        if (userDetails.getUsername().equals(restaurant.getCreatedBy())) {
*/
        restaurantService.deleteById(id);
        return new ResponseEntity<>("Restaurant with id = "+ id + "has been Deleted",HttpStatus.OK);
    }


    @PostMapping(value = "/restaurants/create")
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody Restaurant restaurant,
                                                       BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            logger.info("Error occurred because not all fields are filled!");
            throw new BadRequestException("Please make sure that you fill all fields");
        } else{
            // save Restaurant
            logger.info("New Restaurant was saved successfully.");
            restaurantService.save(restaurant);
            return new ResponseEntity(restaurant, HttpStatus.CREATED);
        }
    }

    @PostMapping("/restaurants/{id}/update")
    public Restaurant updateRestaurant(@PathVariable Long id,
                                       @RequestBody Map<String, Object> restaurantDetails)
    {
        Optional<Restaurant> restaurantOptional = restaurantService.findById(id);
        if (restaurantOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();
            for (Map.Entry<String, Object> entry : restaurantDetails.entrySet()) {
                if( entry.getKey().equals("name") )
                    restaurant.setName((String)entry.getValue());
                else if( entry.getKey().equals("description") )
                    restaurant.setDescription((String)entry.getValue());
                else if( entry.getKey().equals("location") )
                    restaurant.setLocation((String)entry.getValue());
            }
            logger.info("restaurant information edited successfully");
            return restaurantService.save(restaurant);
                } else
                    return null;
            }

}
