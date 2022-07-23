package com.giskard.project.repositories;

import com.giskard.project.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean deleteReservationByStartAndEnd(LocalDateTime start, LocalDateTime end);

}
