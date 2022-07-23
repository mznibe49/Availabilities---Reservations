package com.giskard.project.payload.request;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class AvailabilityRequest {

    @NotBlank
    private LocalDateTime start;

    @NotBlank
    private LocalDateTime end;

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
