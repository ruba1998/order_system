package com.example.order_system.repository;

import com.example.order_system.domain.ContactDetails;
import com.example.order_system.domain.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

    List<OrderDetails> findAllByOrderId(Long orderId);

}
