package com.owl.tripmanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owl.tripmanager.model.Tourist;

public interface TouristRepository extends JpaRepository<Tourist, Long> {
    Optional<Tourist> findByDocument(String document);
    boolean existsByDocument(String document);
}
