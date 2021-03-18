package com.example.order_system.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Meal {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @NotEmpty(message = "The short description for the meal must be filled")
    private String shortDescription;

    @NonNull
    @NotEmpty(message = "The long description for the meal must be filled")
    private String longDescription;

    @Column(nullable = false)
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

}
