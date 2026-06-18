package com.owl.tripmanager.controller;

import com.owl.tripmanager.dto.TouristRequest;
import com.owl.tripmanager.dto.TouristResponse;
import com.owl.tripmanager.model.Tourist;
import com.owl.tripmanager.service.TouristService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tourists")
@RequiredArgsConstructor
public class TouristController {

    private final TouristService touristService;

    // 1. GET: Read all tourists
    @GetMapping
    public ResponseEntity<List<TouristResponse>> getAllTourists() {
        List<TouristResponse> responses = touristService.getAllTourists().stream()
                .map(TouristResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(responses);
    }

    // 2. GET: Read tourist by ID
    @GetMapping("/{id}")
    public ResponseEntity<TouristResponse> getTouristById(@PathVariable Long id) {
        Tourist tourist = touristService.getTouristById(id);
        return ResponseEntity.ok(TouristResponse.fromEntity(tourist));
    }

    // 2.1 GET: Read tourist by Document
    @GetMapping("/document/{document}")
    public ResponseEntity<TouristResponse> getTouristByDocument(@PathVariable String document) {
        Tourist tourist = touristService.getTouristByDocument(document);
        return ResponseEntity.ok(TouristResponse.fromEntity(tourist));
    }

    // 3. POST: Create tourist
    @PostMapping
    public ResponseEntity<TouristResponse> createTourist(@Valid @RequestBody TouristRequest touristRequest) {
        Tourist created = touristService.createTourist(touristRequest);
        return new ResponseEntity<>(TouristResponse.fromEntity(created), HttpStatus.CREATED);
    }

    // 4. PUT: Update tourist
    @PutMapping("/{id}")
    public ResponseEntity<TouristResponse> updateTourist(
            @PathVariable Long id,
            @Valid @RequestBody TouristRequest touristRequest) {
        Tourist updated = touristService.updateTourist(id, touristRequest);
        return ResponseEntity.ok(TouristResponse.fromEntity(updated));
    }

    // 5. DELETE: Delete tourist
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTourist(@PathVariable Long id) {
        touristService.deleteTourist(id);
        return ResponseEntity.noContent().build();
    }
}
