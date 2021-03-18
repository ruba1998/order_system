package com.example.order_system.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetails {

    @EmbeddedId
    private OrderDetailsId id;

    @NonNull
    @Length(max = 11)
    @Column(nullable = false, length = 11)
    private Integer quantity;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;


}
