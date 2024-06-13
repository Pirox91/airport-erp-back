package com.example.pfe.assigned;


import com.example.pfe.user.Role;
import com.example.pfe.user.Type;
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
public class  UserDTO {
    private Integer id;
    private String name;
    private String surname;
    private String type;
    private Integer passport;
}
