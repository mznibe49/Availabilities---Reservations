package com.giskard.project.exceptions.domain;

import com.giskard.project.exceptions.GiskardException;
import com.giskard.project.exceptions.GiskardExceptionStatus;

public class ForbiddenOperationException extends GiskardException {

  public ForbiddenOperationException() {
    super("Forbidden operation", GiskardExceptionStatus.FORBIDDEN);
  }
}
