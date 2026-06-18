package com.owl.tripmanager.dto;

import com.owl.tripmanager.model.Tourist;
import java.time.LocalDate;

public record TouristResponse(
    Long id,
    String firstName,
    String lastName,
    LocalDate birthDate,
    String document
) {
    public static TouristResponse fromEntity(Tourist tourist) {
        return new TouristResponse(
            tourist.getId(),
            tourist.getFirstName(),
            tourist.getLastName(),
            tourist.getBirthDate(),
            tourist.getDocument()
        );
    }
}
