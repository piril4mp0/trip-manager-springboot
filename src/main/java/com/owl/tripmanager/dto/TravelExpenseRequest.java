package com.owl.tripmanager.dto;

import jakarta.validation.constraints.NotNull;

public record TravelExpenseRequest(
    @NotNull(message = "Trip ID is required")
    Long tripId,

    @NotNull(message = "Value is required")
    Integer value,

    String comment
) {}
