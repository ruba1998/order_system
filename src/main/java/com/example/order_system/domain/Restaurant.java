package com.example.order_system.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * this entity represent the restaurant model with all important information about a restaurant
 */

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Restaurant extends Auditable{

    @Id
    @GeneratedValue
    private long id;

    @NonNull
    @NotEmpty(message = "The restaurant must have a name")
    private String name;

    @NonNull
    @NotEmpty(message = "Please add a description for this restaurant")
    private String description;

    @NonNull
    @NotEmpty(message = "The restaurant must have a location")
    private String location;


}
