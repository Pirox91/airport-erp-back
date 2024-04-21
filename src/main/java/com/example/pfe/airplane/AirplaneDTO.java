package com.example.pfe.airplane;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AirplaneDTO {

    private Integer idap;

    @NotNull
    private Boolean available;

    @NotNull
    @Size(max = 255)
    private String model;

}
