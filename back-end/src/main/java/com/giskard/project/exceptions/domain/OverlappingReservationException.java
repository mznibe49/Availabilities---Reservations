package com.giskard.project.exceptions.domain;

import com.giskard.project.exceptions.GiskardException;
import com.giskard.project.exceptions.GiskardExceptionStatus;

public class OverlappingReservationException extends GiskardException {

  public OverlappingReservationException() {
    super(
        "The given reservation is overlapping an existing one", GiskardExceptionStatus.BAD_REQUEST);
  }
}
