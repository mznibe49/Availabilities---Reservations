package com.giskard.project.exceptions.domain;

import com.giskard.project.exceptions.GiskardException;
import com.giskard.project.exceptions.GiskardExceptionStatus;

public class InvalidTimeSlotException extends GiskardException {

  public InvalidTimeSlotException() {
    super("Start must be before end", GiskardExceptionStatus.BAD_REQUEST);
  }
}
