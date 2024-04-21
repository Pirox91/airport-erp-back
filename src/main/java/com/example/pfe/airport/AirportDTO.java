package com.example.pfe.airport;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AirportDTO {

    private Integer idarpt;

    @NotNull
    @Size(max = 255)
    private String name;

}
