package com.owl.tripmanager.repository;

import com.owl.tripmanager.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
