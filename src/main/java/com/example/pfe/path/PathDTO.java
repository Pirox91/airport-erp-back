package com.example.pfe.path;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PathDTO {

    private Integer id;

    @NotNull
    private LocalDateTime departure;

    @NotNull
    private LocalDateTime stopover;

    @NotNull
    private Integer flight;

    @NotNull
    private Integer serie;

}
