package com.giskard.project.exceptions.domain;

import com.giskard.project.exceptions.GiskardException;
import com.giskard.project.exceptions.GiskardExceptionStatus;

public class InvalidTimeSlotException extends GiskardException {

  public InvalidTimeSlotException(String str) {
    super(str, GiskardExceptionStatus.BAD_REQUEST);
  }
}
