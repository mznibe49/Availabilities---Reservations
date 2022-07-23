package com.giskard.project.services;

import com.giskard.project.models.Availability;
import com.giskard.project.models.Reservation;
import com.giskard.project.payload.request.ReservationCreationRequest;
import com.giskard.project.payload.request.ReservationDeletionRequest;
import com.giskard.project.repositories.AvailabilityRepository;
import com.giskard.project.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    AvailabilityRepository availabilityRepository;

    private boolean isOverLapping(Reservation newReservation, Reservation existingReservation){
        LocalDateTime startOfNewReservation = newReservation.getStart();
        LocalDateTime endOfNewReservation = newReservation.getEnd();

        LocalDateTime startOfExistingReservation = existingReservation.getStart();
        LocalDateTime endOfExistingReservation = existingReservation.getEnd();

        // reservation overlapping cases
        //     --
        // [   []    ]
        // -         -
        // [   []    ]
        // - -
        // [[]       ]
        //         - -
        // [       []]
        return (startOfNewReservation.isBefore(startOfExistingReservation) && endOfNewReservation.isAfter(endOfExistingReservation))
        || (startOfNewReservation.isAfter(startOfExistingReservation) && endOfNewReservation.isBefore(endOfExistingReservation))
        || (startOfNewReservation.isAfter(startOfExistingReservation) && startOfNewReservation.isBefore(endOfExistingReservation))
        || (endOfNewReservation.isBefore(endOfExistingReservation) && endOfNewReservation.isBefore(startOfExistingReservation));
    }

    public boolean possibleReservation(ReservationCreationRequest reservationRequest) {
        Optional<Availability> availability = availabilityRepository.findByStartBeforeAndEndAfter(reservationRequest.getStart(), reservationRequest.getEnd());
        if(!availability.isPresent()) return false;

        Set<Reservation> reservationSet = availability.get().getReservations();
        Reservation newReservation = new Reservation(reservationRequest.getStart(),
                reservationRequest.getEnd(),
                reservationRequest.getEmail(),
                reservationRequest.getTitle());

        return reservationSet.stream().noneMatch(
          existingReservation ->  isOverLapping(newReservation, existingReservation)
        );
    }

    public Optional<Reservation> createReservation(ReservationCreationRequest reservationRequest) {

        Optional<Availability> availability = availabilityRepository.findByStartBeforeAndEndAfter(reservationRequest.getStart(), reservationRequest.getEnd());
        // verification is done in controller
        if(!availability.isPresent()) return Optional.empty();


        Reservation reservation = new Reservation(reservationRequest.getStart(),
                reservationRequest.getEnd(),
                reservationRequest.getEmail(),
                reservationRequest.getTitle());

        // Optional<Reservation> registredReservation =
        Availability foundAvailability = availability.get();

        // saving the new reservation in the existing ability
        Set<Reservation> reservationSet = foundAvailability.getReservations();
        reservationSet.add(reservation);
        foundAvailability.setReservations(reservationSet);


        reservationRepository.save(reservation);

        // Est ce que la ca crée une nouvelle ou ça save l'ancienne ??
        availabilityRepository.save(foundAvailability);

        // create custom exception in case the connection to the DB is down ?
        return Optional.of(reservation);
    }


    public boolean canDeleteReservation(ReservationDeletionRequest reservationRequest) {
        return reservationRepository.deleteReservationByStartAndEnd(reservationRequest.getStart(), reservationRequest.getEnd());
    }
}
