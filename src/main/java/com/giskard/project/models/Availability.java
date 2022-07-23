package com.giskard.project.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "availability")
public class Availability {
    private Long id;

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

    @OneToMany(mappedBy = "reservations", cascade = CascadeType.ALL)
    private Set<Reservation> reservations;

    public Availability(){
    }

    public Availability(LocalDateTime start, LocalDateTime end){
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
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
}
