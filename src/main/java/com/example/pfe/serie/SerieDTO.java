package com.example.pfe.serie;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SerieDTO {

    private Integer ids;

    @NotNull
    @Schema(type = "string", example = "18:30")
    private Time durration;

    @NotNull
    private Integer departure;

    @NotNull
    private Integer destination;

    @NotNull
    private Integer airplane;

}
