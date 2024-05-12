package com.example.pfe.airplane;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AirplaneDTO {

    private Integer idap;

    @NotNull
    private Boolean available;

    @NotNull
    @Size(max = 255)
    private String model;

}
