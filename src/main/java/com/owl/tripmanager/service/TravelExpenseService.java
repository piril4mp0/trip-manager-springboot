package com.owl.tripmanager.service;

import com.owl.tripmanager.dto.TravelExpenseRequest;
import com.owl.tripmanager.exception.TravelExpenseNotFoundException;
import com.owl.tripmanager.model.TravelExpense;
import com.owl.tripmanager.model.Trip;
import com.owl.tripmanager.repository.TravelExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelExpenseService {

    private final TravelExpenseRepository expenseRepository;
    private final TripService tripService;

    @Transactional(readOnly = true)
    public List<TravelExpense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public TravelExpense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new TravelExpenseNotFoundException("Travel expense not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<TravelExpense> getExpensesByTripId(Long tripId) {
        tripService.getTripById(tripId);
        return expenseRepository.findByTripId(tripId);
    }

    @Transactional
    public TravelExpense createExpense(TravelExpenseRequest request) {
        Trip trip = tripService.getTripById(request.tripId());
        
        TravelExpense expense = TravelExpense.builder()
                .trip(trip)
                .value(request.value())
                .comment(request.comment())
                .build();
        return expenseRepository.save(expense);
    }

    @Transactional
    public TravelExpense updateExpense(Long id, TravelExpenseRequest request) {
        TravelExpense existing = getExpenseById(id);
        
        if (request.tripId() != null) {
            Trip trip = tripService.getTripById(request.tripId());
            existing.setTrip(trip);
        }
        if (request.value() != null) {
            existing.setValue(request.value());
        }
        existing.setComment(request.comment());

        return expenseRepository.save(existing);
    }

    @Transactional
    public void deleteExpense(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new TravelExpenseNotFoundException("Travel expense not found with id: " + id);
        }
        expenseRepository.deleteById(id);
    }
}
