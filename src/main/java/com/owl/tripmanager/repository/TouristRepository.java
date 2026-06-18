package com.owl.tripmanager.repository;

import com.owl.tripmanager.model.Tourist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TouristRepository extends JpaRepository<Tourist, Long> {
    Optional<Tourist> findByDocument(String document);
    boolean existsByDocument(String document);
}
