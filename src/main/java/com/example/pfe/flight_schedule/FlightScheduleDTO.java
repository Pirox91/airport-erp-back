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

    @NotNull
    private LocalDateTime arrival;

    @NotNull
    private LocalDateTime departure;

    @NotNull
    private Integer airplane;
    private Boolean delay;

    private Integer weekly;

}
