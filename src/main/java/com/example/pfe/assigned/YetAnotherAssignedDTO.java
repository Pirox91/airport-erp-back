package com.example.pfe.assigned;

import com.example.pfe.airplane.AirplaneDTO;
import com.example.pfe.airport.AirportDTO;
import com.example.pfe.flight_schedule.*;
import com.example.pfe.path.Path;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class YetAnotherAssignedDTO {
    private Integer id;
    private YetAnotherFsDTO flight;
    private UserDTO pilot;
    private UserDTO coPilot;
    private UserDTO pnc;
    private UserDTO pnc2;
    private UserDTO pnc3;




}
