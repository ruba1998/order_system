package com.example.order_system.repository;

import com.example.order_system.domain.ContactDetails;
import com.example.order_system.domain.OrderDetails;
import com.example.order_system.domain.OrderDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, OrderDetailsId> {

    List<OrderDetails> findAllByOrderId(Long orderId);

}
