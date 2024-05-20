package com.example.pfe.flight_schedule;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class YetAnotherPathDTO {
    private Integer id;

    @NotNull
    private LocalDateTime departure;

    @NotNull
    private Time stopover;

    @NotNull
    private YetAnotherSerieDTO serie;
}
