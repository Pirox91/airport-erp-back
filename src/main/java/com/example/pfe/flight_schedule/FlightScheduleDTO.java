package com.example.pfe.flight_schedule;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FlightScheduleDTO {

    private Integer idfs;

    @NotNull
    private OffsetDateTime arrival;

    @NotNull
    private OffsetDateTime departure;

    @NotNull
    private Integer airplane;

    private Integer weekly;

}
