package com.giskard.project.exceptions.domain;

import com.giskard.project.exceptions.GiskardException;
import com.giskard.project.exceptions.GiskardExceptionStatus;

public class ReservationNotFittingException extends GiskardException {

  public ReservationNotFittingException() {
    super("Reservation does not fit inside availability", GiskardExceptionStatus.BAD_REQUEST);
  }
}
