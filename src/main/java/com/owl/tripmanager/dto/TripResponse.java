package com.owl.tripmanager.dto;

import com.owl.tripmanager.model.Trip;
import com.owl.tripmanager.model.TripStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public record TripResponse(
    Long id,
    LocalDate startDate,
    LocalDate endDate,
    Integer touristsLimit,
    String tripTitle,
    BigDecimal touristPrice,
    BigDecimal childPrice,
    TripStatus status,
    Set<TouristResponse> tourists
) {
    public static TripResponse fromEntity(Trip trip) {
        Set<TouristResponse> touristResponses = trip.getTourists() != null 
            ? trip.getTourists().stream().map(TouristResponse::fromEntity).collect(Collectors.toSet())
            : Set.of();

        return new TripResponse(
            trip.getId(),
            trip.getStartDate(),
            trip.getEndDate(),
            trip.getTouristsLimit(),
            trip.getTripTitle(),
            trip.getTouristPrice(),
            trip.getChildPrice(),
            trip.getStatus(),
            touristResponses
        );
    }
}
