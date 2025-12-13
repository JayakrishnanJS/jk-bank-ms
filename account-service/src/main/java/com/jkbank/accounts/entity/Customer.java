package com.jkbank.accounts.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseEntity {

    @Id // if BaseEntity has @Id, then no need to declare here
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="customer_id") // specify column name if different from field name(optional since they are the same here)
    private Long customerId;

    private String name;

    private String email;

    private String mobileNumber;
}
