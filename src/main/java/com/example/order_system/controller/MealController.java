package com.example.order_system.controller;

import com.example.order_system.domain.ContactDetails;
import com.example.order_system.domain.Meal;
import com.example.order_system.domain.Restaurant;
import com.example.order_system.exceptionHandler.ContactWithThisTypeAlreadyExist;
import com.example.order_system.exceptionHandler.ResourceNotFoundException;
import com.example.order_system.repository.RestaurantRepository;
import com.example.order_system.service.MealService;
import com.example.order_system.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class MealController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private MealService mealService;
    private RestaurantService restaurantService;

    public MealController(MealService mealService, RestaurantService restaurantService) {
        this.mealService = mealService;
        this.restaurantService = restaurantService;
    }

    @PostMapping("/restaurants/{restaurantId}/meals/create")
    public ResponseEntity<Meal> addMeal(@Valid @RequestBody Meal meal, @PathVariable Long restaurantId) {
        Optional<Restaurant> restaurantOptional = restaurantService.findById(restaurantId);
        if (!restaurantOptional.isPresent()) {
            logger.error("There is an Error of adding a new meal,The given ID is not found ");
            throw new ResourceNotFoundException("RestaurantId " + restaurantId + " not found");
        }
        Restaurant restaurant = restaurantOptional.get();
        meal.setRestaurant(restaurant);
        mealService.save(meal);
        logger.info("New meal added successfully to Restaurant with Id " + restaurantId);
        return new ResponseEntity<>(meal, HttpStatus.CREATED);
    }

    @DeleteMapping("/restaurants/{restaurantId}/meals/{id}")
    public ResponseEntity<String> deleteMealById(@PathVariable Long restaurantId, @PathVariable Long id) {
        Optional<Meal> mealOptional = mealService.findByIdAndRestaurantId(id, restaurantId);
        if (!mealOptional.isPresent()) {
            logger.info("Error occurred because this meal is not found!");
            throw new ResourceNotFoundException("There is no meal with this id for this restaurant id"
                    + restaurantId
                    + ", or one or both ids does not exist");
        }
        mealService.deleteById(id);
        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }

    @GetMapping("/restaurants/{restaurantId}/meals")
    public ResponseEntity<List<Meal>> getAllMeals(@PathVariable Long restaurantId) {
        if (!restaurantService.existsById(restaurantId)) {
            logger.error("There is an Error,The given ID is not found ");
            throw new ResourceNotFoundException("RestaurantId " + restaurantId + " not found");
        }
        List<Meal> mealList = mealService.findAllByRestaurantId(restaurantId);
        return new ResponseEntity<>(mealList, HttpStatus.OK);
    }

    @GetMapping("/restaurants/{restaurantId}/meals/{id}")
    public ResponseEntity<Meal> findMealById(@PathVariable Long restaurantId, @PathVariable Long id) {
        Optional<Meal> mealOptional = mealService.findByIdAndRestaurantId(id, restaurantId);
        if (!mealOptional.isPresent()) {
            logger.info("Error occurred because this meal is not found!");
            throw new ResourceNotFoundException("There is no meal with this id for this restaurant id" +
                    ", or one or both ids does not exist");
        }
        return new ResponseEntity<>(mealOptional.get(), HttpStatus.OK);
    }

    @PostMapping("/restaurants/{restaurantId}/meals/{id}/update")
    public ResponseEntity<Meal> updateMeal(@PathVariable Long restaurantId,
                                           @PathVariable Long id,
                                           @RequestBody Map<String, Object> updatedMeal) {
        Optional<Meal> mealOptional = mealService.findByIdAndRestaurantId(id, restaurantId);
        if (!mealOptional.isPresent()) {
            logger.info("Error occurred because this meal is not found!");
            throw new ResourceNotFoundException("There is no meal with this id for this restaurant id" +
                    ", or one or both ids does not exist");
        }
        Meal meal = mealOptional.get();
        for (Map.Entry<String, Object> entry : updatedMeal.entrySet()) {
            if (entry.getKey().equals("shortDescription"))
                meal.setShortDescription((String) entry.getValue());
            else if (entry.getKey().equals("longDescription"))
                meal.setLongDescription((String) entry.getValue());
            else if (entry.getKey().equals("price"))
                meal.setPrice((Integer) entry.getValue());
        }
        logger.info("Meal's information edited successfully");
        return new ResponseEntity<>(mealService.save(meal), HttpStatus.OK);
    }


    }
