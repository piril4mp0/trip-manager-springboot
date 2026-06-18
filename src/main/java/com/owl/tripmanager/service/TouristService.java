package com.owl.tripmanager.service;

import com.owl.tripmanager.exception.TouristNotFoundException;
import com.owl.tripmanager.model.Tourist;
import com.owl.tripmanager.dto.TouristRequest;
import com.owl.tripmanager.repository.TouristRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
        if (touristRepository.existsByDocument(request.document())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tourist already exists with document: " + request.document());
        }
        Tourist tourist = Tourist.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .birthDate(request.birthDate())
                .document(request.document())
                .build();
        return touristRepository.save(tourist);
    }

    @Transactional
    public Tourist updateTourist(Long id, TouristRequest request) {
        Tourist existingTourist = touristRepository.findById(id)
                .orElseThrow(() -> new TouristNotFoundException("Tourist not found with id: " + id));

        if (!existingTourist.getDocument().equals(request.document()) && 
            touristRepository.existsByDocument(request.document())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tourist already exists with document: " + request.document());
        }

        existingTourist.setFirstName(request.firstName());
        existingTourist.setLastName(request.lastName());
        existingTourist.setBirthDate(request.birthDate());
        existingTourist.setDocument(request.document());

        return touristRepository.save(existingTourist);
    }

    @Transactional
    public void deleteTourist(Long id) {
        if (!touristRepository.existsById(id)) {
            throw new TouristNotFoundException("Tourist not found with id: " + id);
        }
        touristRepository.deleteById(id);
    }
}
