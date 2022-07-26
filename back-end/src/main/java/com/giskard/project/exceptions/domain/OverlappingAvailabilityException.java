package com.giskard.project.exceptions.domain;

import com.giskard.project.exceptions.GiskardException;
import com.giskard.project.exceptions.GiskardExceptionStatus;

public class OverlappingAvailabilityException extends GiskardException {

  public OverlappingAvailabilityException() {
    super(
        "The given availability is overlapping an existing one",
        GiskardExceptionStatus.BAD_REQUEST);
  }
}
