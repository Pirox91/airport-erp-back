package com.example.pfe.assigned;

import com.example.pfe.flight_schedule.FlightScheduleDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class YetAnotherAssignedDTO {
    private Integer id;
    private FlightScheduleDTO flight;
    private UserDTO pilot;
    private UserDTO coPilot;
    private UserDTO pnc;
    private UserDTO pnc2;
    private UserDTO pnc3;
}
