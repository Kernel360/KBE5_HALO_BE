package com.kernel360.Halo.domain.entity;
import jakarta.persistence.*;


@Entity
public class customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;
}
