package com.giskard.project.services;

import com.giskard.project.controllers.payload.request.CreateReservationRequest;
import com.giskard.project.domain.TimeSlot;
import com.giskard.project.exceptions.domain.*;
import com.giskard.project.models.Availability;
import com.giskard.project.models.Reservation;
import com.giskard.project.repositories.AvailabilityRepository;
import com.giskard.project.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReservationService {

  private final ReservationRepository reservationRepository;

  private final AvailabilityRepository availabilityRepository;

  @Autowired
  public ReservationService(
      ReservationRepository reservationRepository, AvailabilityRepository availabilityRepository) {
    this.reservationRepository = reservationRepository;
    this.availabilityRepository = availabilityRepository;
  }

  @Transactional
  public Reservation create(CreateReservationRequest request) {
    Availability availability =
        this.availabilityRepository
            .findById(request.getAvailabilityId())
            .orElseThrow(() -> new NoSuchAvailabilityException(request.getAvailabilityId()));

    TimeSlot availabilityTimeSlot = new TimeSlot(availability.getStart(), availability.getEnd());
    TimeSlot newReservationTimeSlot = new TimeSlot(request.getStart(), request.getEnd());

    if (!availabilityTimeSlot.contains(newReservationTimeSlot)) {
      throw new ReservationNotFittingException();
    }

    availability
        .getReservations()
        .forEach(
            r -> {
              TimeSlot reservationTimeSlot = new TimeSlot(r.getStart(), r.getEnd());

              if (reservationTimeSlot.isOverlappedBy(newReservationTimeSlot)) {
                throw new OverlappingReservationException();
              }
            });

    Reservation reservation =
        new Reservation(
            null,
            availability,
            request.getStart(),
            request.getEnd(),
            request.getEmail(),
            request.getTitle(),
            null);

    availability.getReservations().add(reservation);

    return reservation;
  }

  public void delete(long id, String email) {
    Reservation reservation =
        this.reservationRepository
            .findById(id)
            .orElseThrow(() -> new NoSuchReservationException(id));

    if (!reservation.getReservationEmail().equals(email)) {
      throw new ForbiddenOperationException();
    }

    this.reservationRepository.delete(reservation);
  }
}
