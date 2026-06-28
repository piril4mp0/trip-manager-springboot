package com.owl.tripmanager.service;

import com.owl.tripmanager.dto.TravelExpenseRequest;
import com.owl.tripmanager.exception.TravelExpenseNotFoundException;
import com.owl.tripmanager.model.TravelExpense;
import com.owl.tripmanager.model.Trip;
import com.owl.tripmanager.repository.TravelExpenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
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
        log.info("Creating travel expense of value {} for trip ID: {}", request.value(), request.tripId());
        Trip trip = tripService.getTripById(request.tripId());
        
        TravelExpense expense = TravelExpense.builder()
                .trip(trip)
                .value(request.value())
                .comment(request.comment())
                .build();
        TravelExpense saved = expenseRepository.save(expense);
        log.info("Successfully created travel expense with ID: {}", saved.getId());
        return saved;
    }

    @Transactional
    public TravelExpense updateExpense(Long id, TravelExpenseRequest request) {
        log.info("Updating travel expense with ID: {}", id);
        TravelExpense existing = getExpenseById(id);
        
        if (request.tripId() != null) {
            Trip trip = tripService.getTripById(request.tripId());
            existing.setTrip(trip);
        }
        if (request.value() != null) {
            existing.setValue(request.value());
        }
        existing.setComment(request.comment());

        TravelExpense updated = expenseRepository.save(existing);
        log.info("Successfully updated travel expense with ID: {}", updated.getId());
        return updated;
    }

    @Transactional
    public void deleteExpense(Long id) {
        log.info("Deleting travel expense with ID: {}", id);
        if (!expenseRepository.existsById(id)) {
            throw new TravelExpenseNotFoundException("Travel expense not found with id: " + id);
        }
        expenseRepository.deleteById(id);
        log.info("Successfully deleted travel expense with ID: {}", id);
    }
}
