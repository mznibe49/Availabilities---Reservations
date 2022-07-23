package com.giskard.project.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
public class Reservation {
    private Long id;

    public Reservation() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }

    @CreationTimestamp
    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @CreationTimestamp
    @Column(name = "start")
    private LocalDateTime start;

    @CreationTimestamp
    @Column(name = "end")
    private LocalDateTime end;

    @NotBlank
    @Size(max = 50)
    @Email
    private String reservationEmail; // email of a person making the reservation

    @NotBlank
    @Size(max = 20)
    private String eventTitle;


    @OneToOne
    @JoinColumn(name = "availability_id", referencedColumnName = "id")
    private Availability availability;

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public Reservation(LocalDateTime start, LocalDateTime end, String email, String title) {
        this.start = start;
        this.end = end;
        this.eventTitle = title;
        this.reservationEmail = email;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getReservationEmail() {
        return reservationEmail;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setReservationEmail(String reservationEmail) {
        this.reservationEmail = reservationEmail;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }
}
