package com.example.pfe.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class  UserDTO {

    private Integer id;

    private Boolean available;

    private Boolean validitelicense;

    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String password;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String surname;

    private Role role;
    private Type type;

}
