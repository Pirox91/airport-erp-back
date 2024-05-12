package com.example.pfe.serie;

import com.example.pfe.airport.Airport;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SerieDTO {

    private Integer ids;

    @NotNull
    @Schema(type = "string", example = "18:30")
    private LocalTime durration;

    @NotNull
    private Integer departure;

    @NotNull
    private Integer destination;

    @NotNull
    private Integer airplane;

}
