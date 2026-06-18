package com.owl.tripmanager.dto;

import com.owl.tripmanager.model.TripStatus;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record TripRequest(
    @NotNull(message = "Start date is required")
    LocalDate startDate,

    @NotNull(message = "End date is required")
    LocalDate endDate,

    @NotNull(message = "Tourists limit is required")
    Integer touristsLimit,

    @NotNull(message = "Trip title is required")
    String tripTitle,

    @NotNull(message = "Tourist price is required")
    BigDecimal touristPrice,

    @NotNull(message = "Child price is required")
    BigDecimal childPrice,

    TripStatus status
) {}
