package com.owl.tripmanager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trips")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date", nullable = false)
    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @Column(name = "tourists_limit", nullable = false)
    @NotNull(message = "Tourists limit is required")
    private Integer touristsLimit;

    @Column(name = "trip_title", nullable = false, length = 150)
    @NotNull(message = "Trip title is required")
    private String tripTitle;

    @Column(name = "tourist_price", nullable = false)
    @NotNull(message = "Tourist price is required")
    private BigDecimal touristPrice;

    @Column(name = "child_price", nullable = false)
    @NotNull(message = "Child price is required")
    private BigDecimal childPrice;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Trip status is required")
    @Builder.Default
    private TripStatus status = TripStatus.PENDING;

    @ManyToMany
    @JoinTable(
        name = "trip_tourists",
        joinColumns = @JoinColumn(name = "trip_id"),
        inverseJoinColumns = @JoinColumn(name = "tourist_id")
    )
    @Builder.Default
    private Set<Tourist> tourists = new HashSet<>();
}
