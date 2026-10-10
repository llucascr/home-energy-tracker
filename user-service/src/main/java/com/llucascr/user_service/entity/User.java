package com.llucascr.user_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_email", columnNames = "email")
})
@Entity(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String surname;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(nullable = false)
    private boolean alerting;

    @Column(name = "energy_alerting_threshold", nullable = false)
    private double energyAlertingThreshold;

}
