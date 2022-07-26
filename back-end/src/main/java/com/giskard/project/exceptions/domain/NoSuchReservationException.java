package com.giskard.project.exceptions.domain;

import com.giskard.project.exceptions.NoSuchResourceException;

public class NoSuchReservationException extends NoSuchResourceException {

  public NoSuchReservationException(long id) {
    super(id, "reservation");
  }
}
