package com.example.order_system.controller;

import com.example.order_system.domain.Meal;
import com.example.order_system.domain.Order;
import com.example.order_system.domain.Restaurant;
import com.example.order_system.exceptionHandler.BadRequestException;
import com.example.order_system.exceptionHandler.ResourceNotFoundException;
import com.example.order_system.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/my_orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.findAll();
        if( orders.isEmpty() ) {
            logger.info("You are not make any order");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("All orders fetched");
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderService.findById(id);
        if(optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            logger.info("You are not make any order");
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
        else
            throw new ResourceNotFoundException("Not found Order with id = " + id);
    }


    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            logger.info("Error occurred because not all fields are filled!");
            throw new BadRequestException("Please make sure that you fill all fields " +
                    "or that data type is suitable");
        } else{
            // save Order
            logger.info("New Order was saved successfully.");
            orderService.save(order);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderById(@PathVariable("id") Long id, Principal principal) {
        Optional<Order> optionalOrder = orderService.findById(id);
        if(optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (principal.getName().equals(order.getCreatedBy())) {
                orderService.deleteById(id);
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity("You are not allowed to make this request", HttpStatus.BAD_REQUEST);
            }
        }
        else
            throw new ResourceNotFoundException("Not found Order with id = " + id);
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder) {
        Optional<Order> optionalOrder = orderService.findById(id);
        if(optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setOrderStatus(updatedOrder.getOrderStatus());
            orderService.save(order);
            return new ResponseEntity(order, HttpStatus.OK);
        }
        else
            throw new ResourceNotFoundException("Not found Order with id = " + id);
    }

}
