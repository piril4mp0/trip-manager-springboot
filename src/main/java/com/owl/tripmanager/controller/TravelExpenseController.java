package com.owl.tripmanager.controller;

import com.owl.tripmanager.dto.TravelExpenseRequest;
import com.owl.tripmanager.dto.TravelExpenseResponse;
import com.owl.tripmanager.model.TravelExpense;
import com.owl.tripmanager.service.TravelExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/travel-expenses")
@RequiredArgsConstructor
public class TravelExpenseController {

    private final TravelExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<TravelExpenseResponse>> getAllExpenses() {
        List<TravelExpenseResponse> responses = expenseService.getAllExpenses().stream()
                .map(TravelExpenseResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TravelExpenseResponse> getExpenseById(@PathVariable Long id) {
        TravelExpense expense = expenseService.getExpenseById(id);
        return ResponseEntity.ok(TravelExpenseResponse.fromEntity(expense));
    }

    @PostMapping
    public ResponseEntity<TravelExpenseResponse> createExpense(@Valid @RequestBody TravelExpenseRequest request) {
        TravelExpense created = expenseService.createExpense(request);
        return new ResponseEntity<>(TravelExpenseResponse.fromEntity(created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TravelExpenseResponse> updateExpense(
            @PathVariable Long id, 
            @Valid @RequestBody TravelExpenseRequest request) {
        TravelExpense updated = expenseService.updateExpense(id, request);
        return ResponseEntity.ok(TravelExpenseResponse.fromEntity(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}
