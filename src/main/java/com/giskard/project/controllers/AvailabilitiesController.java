package com.giskard.project.controllers;

import com.giskard.project.models.Availability;
import com.giskard.project.payload.request.AvailabilityRequest;
import com.giskard.project.payload.response.MessageResponse;
import com.giskard.project.services.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/api/availability")
public class AvailabilitiesController {

    @Autowired
    AvailabilityService availabilityService;

    @GetMapping("/list")
    public List<Availability> listAvailabilities() {
        Optional<List<Availability>> optionalList = availabilityService.loadAllAvailabilities();
        return optionalList.orElseGet(ArrayList::new);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAvailability(@Valid @RequestBody AvailabilityRequest newAvailability) {

        if(availabilityService.canCreateAvailability(newAvailability)){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Availability already exists or time slop is overlapping"));
        } else {
            Optional<Availability> availability = availabilityService.createAvailability(newAvailability);
            if(!availability.isPresent()){ // why isEmpty not working
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error occurred while creating availability"));
            } else {
                return new ResponseEntity<>(availability.get(), HttpStatus.CREATED);
            }
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAvailability(@Valid @RequestBody AvailabilityRequest availability) {
        boolean isRemoved = availabilityService.canDeleteAvailability(availability);

        if (!isRemoved) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(availability, HttpStatus.OK);
    }
}
