package com.giskard.project.repositories;

import com.giskard.project.models.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    Optional<List<Availability>> findAllByOrderByStartDesc();

    Optional<Availability> findByStartBeforeAndEndAfter(LocalDateTime start, LocalDateTime end);

    // use this methode with start & end
    //                       start & start
    //                       end & end
    boolean existsAvailabilityByStartBeforeAndEndAfter(LocalDateTime start, LocalDateTime end);

    boolean existsAvailabilityByStartAfterAndEndBefore(LocalDateTime start, LocalDateTime end);

    boolean deleteAvailabilityByStartAndEnd(LocalDateTime start, LocalDateTime end);

}
