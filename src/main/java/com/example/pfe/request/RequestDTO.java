package com.example.pfe.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RequestDTO {

    private Integer id;

    @NotNull
    private Boolean state;

    @NotNull
    private String body;
    @NotNull
    private Boolean viewed;

    @NotNull
    @Size(max = 255)
    private String title;

    private Integer user;

}
