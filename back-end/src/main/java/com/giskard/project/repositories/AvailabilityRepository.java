package com.giskard.project.repositories;

import com.giskard.project.models.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

  List<Availability> findAllByOrderByStartAsc();

  List<Availability> findAllByStartAfterAndEndBefore(LocalDateTime start, LocalDateTime end);

}
