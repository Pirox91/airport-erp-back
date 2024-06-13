package com.example.pfe.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class  UserDTO {

    private Integer id;

    private Boolean available;

    private Boolean validitelicense;

    @Size(max = 255)
    private String email;


    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String surname;
    private Integer passport;

    private Role role;
    private Type type;

}
