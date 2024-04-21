package com.example.pfe.weekly;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class WeeklyDTO {

    private Integer id;

    @Size(max = 255)
    private String name;

}
