package com.owl.tripmanager.service;

import com.owl.tripmanager.exception.TripNotFoundException;
import com.owl.tripmanager.dto.TripRequest;
import com.owl.tripmanager.model.TripStatus;
import com.owl.tripmanager.model.Tourist;
import com.owl.tripmanager.model.Trip;
import com.owl.tripmanager.repository.TripRepository;
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
public class TripService {

    private final TripRepository tripRepository;
    private final TouristService touristService;

    @Transactional(readOnly = true)
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Trip getTripById(Long id) {
        return tripRepository.findById(id)
                .orElseThrow(() -> new TripNotFoundException("Trip not found with id: " + id));
    }

    @Transactional
    public Trip createTrip(TripRequest request) {
        log.info("Creating trip: {}", request.tripTitle());
        TripStatus status = request.status() != null ? request.status() : TripStatus.PENDING;
        Trip trip = Trip.builder()
                .startDate(request.startDate())
                .endDate(request.endDate())
                .touristsLimit(request.touristsLimit())
                .tripTitle(request.tripTitle())
                .touristPrice(request.touristPrice())
                .childPrice(request.childPrice())
                .status(status)
                .build();
        Trip saved = tripRepository.save(trip);
        log.info("Successfully created trip with ID: {}", saved.getId());
        return saved;
    }

    @Transactional
    public Trip updateTrip(Long id, TripRequest request) {
        log.info("Updating trip with ID: {}", id);
        Trip existing = getTripById(id);

        if (request.startDate() != null) {
            existing.setStartDate(request.startDate());
        }
        if (request.endDate() != null) {
            existing.setEndDate(request.endDate());
        }
        if (request.touristsLimit() != null) {
            existing.setTouristsLimit(request.touristsLimit());
        }
        if (request.tripTitle() != null) {
            existing.setTripTitle(request.tripTitle());
        }
        if (request.touristPrice() != null) {
            existing.setTouristPrice(request.touristPrice());
        }
        if (request.childPrice() != null) {
            existing.setChildPrice(request.childPrice());
        }
        if (request.status() != null) {
            existing.setStatus(request.status());
        }

        Trip updated = tripRepository.save(existing);
        log.info("Successfully updated trip with ID: {}", updated.getId());
        return updated;
    }

    @Transactional
    public void deleteTrip(Long id) {
        log.info("Deleting trip with ID: {}", id);
        if (!tripRepository.existsById(id)) {
            throw new TripNotFoundException("Trip not found with id: " + id);
        }
        tripRepository.deleteById(id);
        log.info("Successfully deleted trip with ID: {}", id);
    }

    @Transactional
    public Trip subscribeTourist(Long tripId, Long touristId) {
        log.info("Attempting to subscribe tourist ID: {} to trip ID: {}", touristId, tripId);
        Trip trip = getTripById(tripId);
        Tourist tourist = touristService.getTouristById(touristId);

        if (trip.getTourists().contains(tourist)) {
            log.warn("Tourist ID: {} is already subscribed to trip ID: {}", touristId, tripId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tourist is already subscribed to this trip");
        }

        if (trip.getTourists().size() >= trip.getTouristsLimit()) {
            log.warn("Trip ID: {} capacity limit reached: {}", tripId, trip.getTouristsLimit());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trip capacity limit reached: " + trip.getTouristsLimit());
        }

        trip.getTourists().add(tourist);
        Trip saved = tripRepository.save(trip);
        log.info("Successfully subscribed tourist ID: {} to trip ID: {}", touristId, tripId);
        return saved;
    }
}
