package com.example.order_system.controller;

import com.example.order_system.domain.ContactDetails;
import com.example.order_system.domain.Restaurant;
import com.example.order_system.exceptionHandler.BadRequestException;
import com.example.order_system.exceptionHandler.InternalServerErrorException;
import com.example.order_system.service.ContactDetailsService;
import com.example.order_system.service.RestaurantService;
import com.example.order_system.utils.JsonNullableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Null;
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
    public List<Restaurant> getRestaurants() {
        List<Restaurant> restaurants = restaurantService.findAll();
        logger.info("All restaurants fetched");
        return restaurants;
    }

    @GetMapping("/{id}")
    public Restaurant findRestaurantById(@PathVariable("id") Long id) {
        Optional<Restaurant> optionalRestaurant = restaurantService.findById(id);
        return optionalRestaurant.get();
    }

    @DeleteMapping("/{id}")
    public String deleteRestaurantById(@PathVariable("id") Long id) throws InternalServerErrorException {
        if(restaurantService.existsById(id) == false){
            logger.info("Error occurred because this restaurant is not found!");
            throw new InternalServerErrorException("There is no restaurant with this id");
        }
        restaurantService.deleteById(id);
        return "deleted";
    }

    @PostMapping("/restaurant/create")
    public Restaurant createRestaurant(@Valid Restaurant restaurant, BindingResult bindingResult)
            throws BadRequestException
    {
        if(bindingResult.hasErrors()){
            logger.info("Error occurred because not all fields are filled!");
            throw new BadRequestException("Please make sure that you fill all fields");
        }
        else{
            // save Restaurant
            logger.info("New Restaurant was saved successfully.");
            return restaurantService.save(restaurant);
        }
    }

    @PostMapping("/restaurant/{id}/update")
    public Restaurant updateRestaurant(@PathVariable Long id, @RequestBody Map<String, Object> restaurantDetails, BindingResult bindingResult)
            throws NullPointerException
    {
            if(bindingResult.hasErrors()){
                throw new NullPointerException("Please make sure that you fill all fields");
            }

            else {
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

}
