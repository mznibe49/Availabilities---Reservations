package com.giskard.project.domain;

import com.giskard.project.exceptions.domain.InvalidTimeSlotException;

import java.time.LocalDateTime;

public class TimeSlot {

  private final LocalDateTime start;
  private final LocalDateTime end;

  public TimeSlot(LocalDateTime start, LocalDateTime end) {
    if (start == null) {
      throw new IllegalArgumentException("Missing mandatory field start");
    }

    if (end == null) {
      throw new IllegalArgumentException("Missing mandatory field end");
    }

    if (start.isAfter(end)) {
      throw new InvalidTimeSlotException();
    }

    this.start = start;
    this.end = end;
  }

  //

  public boolean contains(TimeSlot other) {
    return (this.start.isBefore(other.start) || this.start.isEqual(other.start))
        && (this.end.isAfter(other.end) || this.end.isEqual(other.end));
  }

  public boolean isOverlappedBy(TimeSlot other) {
    if (this.start.isEqual(other.start) && this.end.isEqual(other.end)) {
      return true;
    }

    return this.contains(other.start) || this.contains(other.end);
  }

  private boolean contains(LocalDateTime date) {
    return date.isAfter(this.start) && date.isBefore(this.end);
  }

  public LocalDateTime getStart() {
    return start;
  }

  public LocalDateTime getEnd() {
    return end;
  }
}
