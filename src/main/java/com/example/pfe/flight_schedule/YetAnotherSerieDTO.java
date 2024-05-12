package com.example.pfe.flight_schedule;

import com.example.pfe.airport.AirportDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class YetAnotherSerieDTO {

    @NotNull
    @Schema(type = "string", example = "18:30")
    private LocalTime durration;

    @NotNull
    private AirportDTO departure;

    @NotNull
    private AirportDTO destination;
}
