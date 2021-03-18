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

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/orders/{orderId}")
public class OrderDetailsController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

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

    @GetMapping("/order_details")
    public ResponseEntity<List<OrderDetails>> getAllOrderDetails(@PathVariable Long orderId) {
        if (!orderService.existsById(orderId)) {
            logger.error("There is an Error of adding a new details,The given ID is not found ");
            throw new ResourceNotFoundException("Order Id " + orderId + " is not found");
        } else {
            List<OrderDetails> orderDetailsList = orderDetailsService.findAllByOrderId(orderId);
            return new ResponseEntity<>(orderDetailsList, HttpStatus.OK);
        }
    }

    @GetMapping("/order_details/{id}")
    public ResponseEntity<OrderDetails> getOrderDetailsById(@PathVariable Long orderId, @PathVariable Long id) {
        if(!orderService.existsById(orderId)){
            logger.info("Error occurred because this order is not found!");
            throw new ResourceNotFoundException("There is no order with this id");
        }
        Optional<OrderDetails> optionalOrderDetails = orderDetailsService.findById(id);
        if(!optionalOrderDetails.isPresent()){
            logger.info("Error occurred because this details is not found!");
            throw new ResourceNotFoundException("There is no order details with this id");
        }
        return new ResponseEntity(optionalOrderDetails.get(),HttpStatus.OK);
    }


    @DeleteMapping("/order_details/{id}")
    public ResponseEntity deleteOrderDetailsById(@PathVariable Long orderId,@PathVariable Long id) {
        if(!orderService.existsById(orderId)){
            logger.info("Error occurred because this order is not found!");
            throw new ResourceNotFoundException("There is no order with this id");
        }
        else if(!orderDetailsService.existsById(id)){
            logger.info("Error occurred because this details is not found!");
            throw new ResourceNotFoundException("There is no order details with this id");
        }
        else {
            orderDetailsService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("order_details/{mealId}/create")
    public ResponseEntity<OrderDetails> addOrderDetails(@RequestBody OrderDetails orderDetails,
                                                        @PathVariable Long orderId,
                                                        @PathVariable Long mealId) {

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
        orderDetails.setOrder(order);
        Meal meal = optionalMeal.get();
        orderDetails.setMeal(meal);
        orderDetailsService.save(orderDetails);
        logger.info("New details was saved successfully to order with Id " + orderId);
        return new ResponseEntity<>(orderDetails, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<OrderDetails> updateOrder(@PathVariable Long orderId,
                                                    @PathVariable Long id,
                                                    @RequestBody OrderDetails updatedDetails) {

        Optional<Order> orderOptional = orderService.findById(orderId);
        Optional<OrderDetails> orderDetailsOptional = orderDetailsService.findById(id);
        if (!orderOptional.isPresent()) {
            logger.error("There is an Error of updating the order details,The given ID is not found ");
            throw new ResourceNotFoundException("Order Id " + orderId + " not found");
        }
        else if (!orderDetailsOptional.isPresent()) {
            logger.error("There is an Error of updating the order details,The given ID is not found ");
            throw new ResourceNotFoundException("Order details Id " + id + " not found");
        }
        else {
            OrderDetails orderDetails = orderDetailsOptional.get();
            orderDetails.setQuantity(updatedDetails.getQuantity());
            orderDetailsService.save(orderDetails);
            logger.info("order details information edited successfully");
            return new ResponseEntity(orderDetails, HttpStatus.OK);
        }

    }


}
