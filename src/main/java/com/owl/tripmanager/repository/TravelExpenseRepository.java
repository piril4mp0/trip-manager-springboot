package com.owl.tripmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owl.tripmanager.model.TravelExpense;

public interface TravelExpenseRepository extends JpaRepository<TravelExpense, Long> {
    List<TravelExpense> findByTripId(Long tripId);
}
