package com.example.order_system.controller;

import com.example.order_system.domain.*;
import com.example.order_system.exceptionHandler.ResourceNotFoundException;
import com.example.order_system.service.MealService;
import com.example.order_system.service.OrderDetailsService;
import com.example.order_system.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("")
public class OrderDetailsController {

    private static final Logger logger = LoggerFactory.getLogger(OrderDetailsController.class);

    private OrderService orderService;
    private OrderDetailsService orderDetailsService;
    private MealService mealService;

    public OrderDetailsController(OrderService orderService,
                                  OrderDetailsService orderDetailsService,
                                  MealService mealService) {
        this.orderService = orderService;
        this.orderDetailsService = orderDetailsService;
        this.mealService = mealService;
    }

    @GetMapping("/order_details/{orderId}")
    public ResponseEntity<List<OrderDetails>> getAllOrderDetails(@PathVariable Long orderId) {
        if (!orderService.existsById(orderId)) {
            logger.error("There is an Error of adding a new details,The given ID is not found ");
            throw new ResourceNotFoundException("Order Id " + orderId + " is not found");
        } else {
            List<OrderDetails> orderDetailsList = orderDetailsService.findAllByOrderId(orderId);
            return new ResponseEntity<>(orderDetailsList, HttpStatus.OK);
        }
    }

    @GetMapping("/order_details/{orderId}/{mealId}")
    public ResponseEntity<OrderDetails> getOrderDetailsById(@PathVariable Long orderId, @PathVariable Long mealId) {
        if(!orderService.existsById(orderId)){
            logger.info("Error occurred because this order is not found!");
            throw new ResourceNotFoundException("There is no order with this id");
        } else if(!mealService.existsById(mealId)){
            logger.info("Error occurred because this meal is not found!");
            throw new ResourceNotFoundException("There is no meal with this id");
        }
        OrderDetailsId orderDetailsId = new OrderDetailsId(orderId, mealId);
        Optional<OrderDetails> optionalOrderDetails = orderDetailsService.findByDetailsId(orderDetailsId);
        if(!optionalOrderDetails.isPresent()){
            logger.info("Error occurred because this details is not found!");
            throw new ResourceNotFoundException("There is no order details with this id");
        }
        return new ResponseEntity(optionalOrderDetails.get(),HttpStatus.OK);
    }


    @DeleteMapping("/order_details/{orderId}/{mealId}")
    public ResponseEntity deleteOrderDetailsById(@PathVariable Long orderId,@PathVariable Long mealId) {
        if(!orderService.existsById(orderId)) {
            logger.info("Error occurred because this order is not found!");
            throw new ResourceNotFoundException("There is no order with this id");
        } else if(!mealService.existsById(mealId)){
            logger.info("Error occurred because this meal is not found!");
            throw new ResourceNotFoundException("There is no meal with this id");
        }
        OrderDetailsId orderDetailsId = new OrderDetailsId(orderId, mealId);
        Optional<OrderDetails> optionalOrderDetails = orderDetailsService.findByDetailsId(orderDetailsId);
        if(!optionalOrderDetails.isPresent()){
            logger.info("Error occurred because this details is not found!");
            throw new ResourceNotFoundException("There is no order details with this id");
        }
        else {
            orderDetailsService.deleteById(orderDetailsId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("order_details/{orderId}/{mealId}/create")
    public ResponseEntity<OrderDetails> addOrderDetails(@RequestBody OrderDetails orderDetails,
                                                        @PathVariable Long orderId,
                                                        @PathVariable Long mealId,
                                                        Principal principal) {
        Optional<Order> optionalOrder = orderService.findById(orderId);
        Optional<Meal> optionalMeal = mealService.findById(mealId);
        if (!optionalOrder.isPresent()) {
            logger.error("There is an Error of adding a new details,The given ID is not found ");
            throw new ResourceNotFoundException("Order Id " + orderId + " not found");
        } else if (!optionalMeal.isPresent()) {
            logger.error("There is an Error of adding a new details,The given ID is not found ");
            throw new ResourceNotFoundException("Meal Id " + mealId + " not found");
        }
        Order order = optionalOrder.get();
        Meal meal = optionalMeal.get();
        if ( !principal.getName().equals(order.getCreatedBy()) ){
            return new ResponseEntity("You are not created the order with id " + orderId + ", you can't add details for it",
                    HttpStatus.BAD_REQUEST);
        } else {
            OrderDetailsId orderDetailsId = new OrderDetailsId();
            orderDetailsId.setOrderId(orderId);
            orderDetailsId.setMealId(mealId);
            orderDetails.setOrder(order);
            orderDetails.setMeal(meal);
            orderDetails.setId(orderDetailsId);
            orderDetails.setCreator(order.getCreatedBy());
            orderDetailsService.save(orderDetails);
            logger.info("New details was saved successfully to order with Id " + orderId);
            return new ResponseEntity<>(orderDetails, HttpStatus.CREATED);
        }
    }

    @PostMapping("/order_details/{orderId}/{mealId}/update")
    public ResponseEntity<OrderDetails> updateOrder(@PathVariable Long orderId,
                                                    @PathVariable Long mealId,
                                                    @RequestBody OrderDetails updatedDetails) {
        if(!orderService.existsById(orderId)) {
            logger.info("Error occurred because this order is not found!");
            throw new ResourceNotFoundException("There is no order with this id");
        } else if(!mealService.existsById(mealId)){
            logger.info("Error occurred because this meal is not found!");
            throw new ResourceNotFoundException("There is no meal with this id");
        }
        OrderDetailsId orderDetailsId = new OrderDetailsId(orderId, mealId);
        Optional<OrderDetails> optionalOrderDetails = orderDetailsService.findByDetailsId(orderDetailsId);
        if(optionalOrderDetails.isPresent()) {
            OrderDetails orderDetails = optionalOrderDetails.get();
            orderDetails.setQuantity(updatedDetails.getQuantity());
            orderDetailsService.save(orderDetails);
            logger.info("order details information edited successfully");
            return new ResponseEntity(orderDetails, HttpStatus.OK);
        } else {
            logger.info("Error occurred because this details not found!");
            throw new ResourceNotFoundException("There is no details with this id");
        }

    }


}
