package com.example.pfe.flight_schedule;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private LocalDateTime stopover;

    @NotNull
    private YetAnotherSerieDTO serie;
}
