package com.owl.tripmanager.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public record TouristRequest(
    @NotNull(message = "First name is required")
    String firstName,

    @NotNull(message = "Last name is required")
    String lastName,

    @NotNull(message = "Birth date is required")
    LocalDate birthDate,

    @NotNull(message = "Document is required")
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "Document must follow the pattern xxx.xxx.xxx-xx")
    String document
) {}
