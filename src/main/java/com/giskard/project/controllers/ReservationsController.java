package com.giskard.project.controllers;

import com.giskard.project.models.Reservation;
import com.giskard.project.payload.request.ReservationCreationRequest;
import com.giskard.project.payload.request.ReservationDeletionRequest;
import com.giskard.project.payload.response.MessageResponse;
import com.giskard.project.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/api/reservation")
public class ReservationsController {

    @Autowired
    ReservationService reservationService;

    @PostMapping("/create")
    public ResponseEntity<?> createReservation(@Valid @RequestBody ReservationCreationRequest newReservation) {
        if(reservationService.possibleReservation(newReservation)){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("This time slot is already reserved or time slot is overlapping"));
        } else {
            Optional<Reservation> reservation = reservationService.createReservation(newReservation);
            if(!reservation.isPresent()){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error occurred while creating a reservation"));
            } else {
                return new ResponseEntity<>(reservation.get(), HttpStatus.CREATED);
            }
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteReservation(@Valid @RequestBody ReservationDeletionRequest reservationRequest){
        boolean isRemoved = reservationService.canDeleteReservation(reservationRequest);

        if (!isRemoved) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(reservationRequest, HttpStatus.OK);
    }
}
