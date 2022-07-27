package com.giskard.project.controllers;

import com.giskard.project.controllers.payload.request.CreateReservationRequest;
import com.giskard.project.controllers.payload.request.DeleteReservationRequest;
import com.giskard.project.controllers.payload.response.AvailabilityResponse;
import com.giskard.project.controllers.payload.response.ReservationResponse;
import com.giskard.project.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationsController {

  private final ReservationService reservationService;

  @Autowired
  public ReservationsController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @GetMapping
  public List<ReservationResponse> listReservations() {
    return reservationService.loadAllReservations().stream()
            .map(ReservationResponse::of)
            .collect(Collectors.toList());
  }
  @PostMapping
  public ReservationResponse createReservation(
      @Valid @RequestBody CreateReservationRequest newReservation) {
    return ReservationResponse.of(this.reservationService.create(newReservation));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteReservation(
      @PathVariable int id, @Valid @RequestBody DeleteReservationRequest request) {
    this.reservationService.delete(id, request.getEmail());
  }
}
