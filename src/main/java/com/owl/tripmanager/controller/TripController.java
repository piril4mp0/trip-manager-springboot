package com.owl.tripmanager.controller;

import com.owl.tripmanager.dto.TripRequest;
import com.owl.tripmanager.dto.TripResponse;
import com.owl.tripmanager.dto.SubscriptionRequest;
import com.owl.tripmanager.dto.TravelExpenseResponse;
import com.owl.tripmanager.model.Trip;
import com.owl.tripmanager.service.TripService;
import com.owl.tripmanager.service.TravelExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;
    private final TravelExpenseService travelExpenseService;

    // 1. GET: Read all trips
    @GetMapping
    public ResponseEntity<List<TripResponse>> getAllTrips() {
        List<TripResponse> responses = tripService.getAllTrips().stream()
                .map(TripResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(responses);
    }

    // 2. GET: Read trip by ID
    @GetMapping("/{id}")
    public ResponseEntity<TripResponse> getTripById(@PathVariable Long id) {
        Trip trip = tripService.getTripById(id);
        return ResponseEntity.ok(TripResponse.fromEntity(trip));
    }

    // 3. POST: Create trip
    @PostMapping
    public ResponseEntity<TripResponse> createTrip(@Valid @RequestBody TripRequest tripRequest) {
        Trip created = tripService.createTrip(tripRequest);
        return new ResponseEntity<>(TripResponse.fromEntity(created), HttpStatus.CREATED);
    }

    // 4. PUT: Update trip (Fields are optional)
    @PutMapping("/{id}")
    public ResponseEntity<TripResponse> updateTrip(@PathVariable Long id, @RequestBody TripRequest tripRequest) {
        Trip updated = tripService.updateTrip(id, tripRequest);
        return ResponseEntity.ok(TripResponse.fromEntity(updated));
    }

    // 5. DELETE: Delete trip
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }

    // 6. POST: Subscribe tourist to trip
    @PostMapping("/{tripId}/tourists")
    public ResponseEntity<TripResponse> subscribeTourist(
            @PathVariable Long tripId,
            @Valid @RequestBody SubscriptionRequest request) {
        Trip trip = tripService.subscribeTourist(tripId, request.touristId());
        return ResponseEntity.ok(TripResponse.fromEntity(trip));
    }

    @GetMapping("/{tripId}/expenses")
    public ResponseEntity<List<TravelExpenseResponse>> getExpensesByTripId(@PathVariable Long tripId) {
        List<TravelExpenseResponse> responses = travelExpenseService.getExpensesByTripId(tripId).stream()
                .map(TravelExpenseResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(responses);
    }
}
