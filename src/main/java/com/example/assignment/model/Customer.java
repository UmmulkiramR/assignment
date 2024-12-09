package com.example.assignment.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Entity(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String middleName;

    @Column(unique=true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;
}
