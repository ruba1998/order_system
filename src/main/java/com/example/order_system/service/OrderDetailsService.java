package com.example.order_system.service;

import com.example.order_system.domain.ContactDetails;
import com.example.order_system.domain.Order;
import com.example.order_system.domain.OrderDetails;
import com.example.order_system.repository.OrderDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;

    public OrderDetailsService(OrderDetailsRepository orderDetailsRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
    }

    public List<OrderDetails> findAllByOrderId(Long orderId){
        return orderDetailsRepository.findAllByOrderId(orderId);
    }

    public OrderDetails save(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }

    public Optional<OrderDetails> findById(Long id) {
        return orderDetailsRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return orderDetailsRepository.existsById(id);
    }

    public void deleteById(Long id) {
        orderDetailsRepository.deleteById(id);
    }



}
