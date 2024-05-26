package com.example.pfe.flight_schedule;

import com.example.pfe.airport.AirportDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class YetAnotherSerieDTO {
    @NotNull
    private Integer ids;
    @NotNull
    @Schema(type = "string", example = "18:30")
    private Time durration;

    @NotNull
    private AirportDTO departure;

    @NotNull
    private AirportDTO destination;
}
