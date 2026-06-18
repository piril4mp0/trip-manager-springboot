package com.owl.tripmanager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "tourists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tourist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName", nullable = false)
    @NotNull(message = "First name is required")
    private String firstName;

    @Column(name = "lastName", nullable = false)
    @NotNull(message = "Last name is required")
    private String lastName;

    @Column(name = "birthDate", nullable = false)
    @NotNull(message = "Birth date is required")
    private LocalDate birthDate;

    @Column(nullable = false, unique = true)
    @NotNull(message = "Document is required")
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "Document must follow the pattern xxx.xxx.xxx-xx")
    private String document;
}
