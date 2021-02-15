package com.example.order_system.domain;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;


@Entity
@Getter @Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue
    private Long id;


    private String status;

    @NotEmpty(message = "Please enter the order's time")
    private String time;

    @ManyToOne
    private User createdBy;

}
