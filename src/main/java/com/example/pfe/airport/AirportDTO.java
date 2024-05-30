package com.example.pfe.airport;

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
public class AirportDTO {

    private Integer idarpt;

    @NotNull
    @Size(max = 255)
    private String name;
    private String country;
    private String city;

}
