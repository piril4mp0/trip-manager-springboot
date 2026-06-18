package com.owl.tripmanager.dto;

import jakarta.validation.constraints.NotNull;

public record SubscriptionRequest(
    @NotNull(message = "Tourist ID is required")
    Long touristId
) {}
