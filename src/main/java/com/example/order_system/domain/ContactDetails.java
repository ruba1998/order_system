package com.example.order_system.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * We need ContactDetails Entity To specify a list of contact details for each restaurant.
 */

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ContactDetails extends Auditable {

    @Id
    @GeneratedValue
    private long id;

    @NonNull
    @NotEmpty(message = "Please enter the contact type")
    private String type;

    @NonNull
    @NotEmpty(message = "Please enter the contact value")
    private String value;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

}
