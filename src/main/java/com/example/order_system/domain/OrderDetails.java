package com.example.order_system.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetails {

    @EmbeddedId
    private OrderDetailsId id;

    @NotNull(message = "Please enter id")
    private Integer quantity;

    @CreatedBy
    private String creator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("orderDetailsList")
    @JoinColumn(name = "order_id", nullable = false, insertable = false, updatable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "meal_id", nullable = false, insertable = false, updatable = false)
    private Meal meal;
}
