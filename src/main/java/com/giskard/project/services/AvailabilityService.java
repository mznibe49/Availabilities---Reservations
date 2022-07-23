package com.giskard.project.services;

import com.giskard.project.models.Availability;
import com.giskard.project.payload.request.AvailabilityRequest;
import com.giskard.project.repositories.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvailabilityService {

    AvailabilityRepository availabilityRepository;

    public Optional<List<Availability>> loadAllAvailabilities() {
        return availabilityRepository.findAllByOrderByStartDesc();
    }

    public Optional<Availability> createAvailability(AvailabilityRequest newAvailability) {
        Availability availability = new Availability(newAvailability.getStart(), newAvailability.getEnd());
        // create custom exception in case the connection to the DB is down ?
        return Optional.of(availabilityRepository.save(availability));
    }

    public boolean canCreateAvailability(AvailabilityRequest availability){

        // Availability overlapping cases
        //     --
        // [   []    ]
        // -         -
        // [   []    ]
        // - -
        // [[]       ]
        //         - -
        // [       []]
        return !availabilityRepository.existsAvailabilityByStartAfterAndEndBefore(availability.getStart(), availability.getEnd())
                && !availabilityRepository.existsAvailabilityByStartBeforeAndEndAfter(availability.getStart(), availability.getEnd())
                && !availabilityRepository.existsAvailabilityByStartBeforeAndEndAfter(availability.getStart(), availability.getStart())
                && !availabilityRepository.existsAvailabilityByStartBeforeAndEndAfter(availability.getEnd(),   availability.getEnd());
    }

    public boolean canDeleteAvailability(AvailabilityRequest availability) {
        return availabilityRepository.deleteAvailabilityByStartAndEnd(availability.getStart(), availability.getEnd());
    }
}
