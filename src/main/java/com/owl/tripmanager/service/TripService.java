package com.owl.tripmanager.service;

import com.owl.tripmanager.exception.TripNotFoundException;
import com.owl.tripmanager.dto.TripRequest;
import com.owl.tripmanager.model.TripStatus;
import com.owl.tripmanager.model.Tourist;
import com.owl.tripmanager.model.Trip;
import com.owl.tripmanager.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
        return tripRepository.save(trip);
    }

    @Transactional
    public Trip updateTrip(Long id, TripRequest request) {
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

        return tripRepository.save(existing);
    }

    @Transactional
    public void deleteTrip(Long id) {
        if (!tripRepository.existsById(id)) {
            throw new TripNotFoundException("Trip not found with id: " + id);
        }
        tripRepository.deleteById(id);
    }

    @Transactional
    public Trip subscribeTourist(Long tripId, Long touristId) {
        Trip trip = getTripById(tripId);
        Tourist tourist = touristService.getTouristById(touristId);

        // Check if tourist is already subscribed
        if (trip.getTourists().contains(tourist)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tourist is already subscribed to this trip");
        }

        // Check capacity limit
        if (trip.getTourists().size() >= trip.getTouristsLimit()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trip capacity limit reached: " + trip.getTouristsLimit());
        }

        trip.getTourists().add(tourist);
        return tripRepository.save(trip);
    }
}
