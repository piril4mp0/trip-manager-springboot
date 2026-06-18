package com.owl.tripmanager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_model", nullable = false)
    @NotNull(message = "Vehicle model is required")
    private String vehicleModel;

    @Column(name = "license_plate", nullable = false, unique = true)
    @NotNull(message = "License plate is required")
    private String licensePlate;

    @Column(nullable = false)
    @NotNull(message = "Year is required")
    @Min(value = 1886, message = "Year must be valid")
    private Integer year;

    @Column(nullable = false)
    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    @Column(name = "vehicle_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Vehicle type is required")
    private VehicleType vehicleType;
}
