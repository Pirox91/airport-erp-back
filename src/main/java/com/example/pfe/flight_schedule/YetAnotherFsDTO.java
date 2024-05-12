package com.example.pfe.flight_schedule;

import com.example.pfe.airplane.Airplane;
import com.example.pfe.airplane.AirplaneDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class YetAnotherFsDTO {

    private Integer idfs;

    private LocalDateTime arrival;

    private LocalDateTime departure;

    private AirplaneDTO airplane;

    private Set<YetAnotherPathDTO> path;

}
