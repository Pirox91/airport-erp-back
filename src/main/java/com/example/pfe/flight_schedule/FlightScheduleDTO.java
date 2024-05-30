package com.example.pfe.flight_schedule;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FlightScheduleDTO {
    private Integer idfs;
    private LocalDateTime arrival;
    private LocalDateTime departure;
    private Integer airplane;
    private Boolean delay;
}
