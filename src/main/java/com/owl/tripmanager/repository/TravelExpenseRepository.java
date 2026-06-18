package com.owl.tripmanager.repository;

import com.owl.tripmanager.model.TravelExpense;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelExpenseRepository extends JpaRepository<TravelExpense, Long> {
    List<TravelExpense> findByTripId(Long tripId);
}
