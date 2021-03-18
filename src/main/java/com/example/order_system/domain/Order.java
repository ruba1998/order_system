package com.example.order_system.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "orders")
public class Order extends Auditable {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @CreatedDate
    private LocalDateTime time;

/*
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator;
*/

}
