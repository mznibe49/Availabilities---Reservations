package com.giskard.project.exceptions.domain;

import com.giskard.project.exceptions.NoSuchResourceException;

public class NoSuchAvailabilityException extends NoSuchResourceException {

  public NoSuchAvailabilityException(long id) {
    super(id, "availability");
  }
}
