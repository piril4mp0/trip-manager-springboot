package com.owl.tripmanager.service;

import com.owl.tripmanager.exception.TouristNotFoundException;
import com.owl.tripmanager.model.Tourist;
import com.owl.tripmanager.dto.TouristRequest;
import com.owl.tripmanager.repository.TouristRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TouristService {

    private final TouristRepository touristRepository;

    @Transactional(readOnly = true)
    public List<Tourist> getAllTourists() {
        return touristRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Tourist getTouristById(Long id) {
        return touristRepository.findById(id)
                .orElseThrow(() -> new TouristNotFoundException("Tourist not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Tourist getTouristByDocument(String document) {
        return touristRepository.findByDocument(document)
                .orElseThrow(() -> new TouristNotFoundException("Tourist not found with document: " + document));
    }

    @Transactional
    public Tourist createTourist(TouristRequest request) {
        log.info("Creating tourist: {} {}", request.firstName(), request.lastName());
        if (touristRepository.existsByDocument(request.document())) {
            log.warn("Failed to create tourist: document {} already exists", request.document());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tourist already exists with document: " + request.document());
        }
        Tourist tourist = Tourist.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .birthDate(request.birthDate())
                .document(request.document())
                .build();
        Tourist saved = touristRepository.save(tourist);
        log.info("Successfully created tourist with ID: {}", saved.getId());
        return saved;
    }

    @Transactional
    public Tourist updateTourist(Long id, TouristRequest request) {
        log.info("Updating tourist with ID: {}", id);
        Tourist existingTourist = touristRepository.findById(id)
                .orElseThrow(() -> new TouristNotFoundException("Tourist not found with id: " + id));

        if (!existingTourist.getDocument().equals(request.document()) && 
            touristRepository.existsByDocument(request.document())) {
            log.warn("Failed to update tourist ID: {} - document {} already exists", id, request.document());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tourist already exists with document: " + request.document());
        }

        existingTourist.setFirstName(request.firstName());
        existingTourist.setLastName(request.lastName());
        existingTourist.setBirthDate(request.birthDate());
        existingTourist.setDocument(request.document());

        Tourist updated = touristRepository.save(existingTourist);
        log.info("Successfully updated tourist with ID: {}", updated.getId());
        return updated;
    }

    @Transactional
    public void deleteTourist(Long id) {
        log.info("Deleting tourist with ID: {}", id);
        if (!touristRepository.existsById(id)) {
            throw new TouristNotFoundException("Tourist not found with id: " + id);
        }
        touristRepository.deleteById(id);
        log.info("Successfully deleted tourist with ID: {}", id);
    }
}
