package com.example.pfe.flight_schedule;

import com.example.pfe.airplane.AirplaneDTO;
import com.example.pfe.airport.AirportDTO;
import com.example.pfe.path.Path;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class YetAnotherFsDTO {

    private Integer idfs;

    private LocalDateTime arrival;

    private LocalDateTime departure;

    private AirplaneDTO airplane;

    private Set<YetAnotherPathDTO> path;
    public void setterForAll(FlightSchedule flightSchedule){
        this.setIdfs(flightSchedule.getIdfs());
        this.setArrival(flightSchedule.getArrival());
        this.setDeparture(flightSchedule.getDeparture());
        this.setAirplane(flightSchedule.getAirplane() == null ? null :new AirplaneDTO(
                flightSchedule.getAirplane().getIdap(),
                flightSchedule.getAirplane().getAvailable(),
                flightSchedule.getAirplane().getModel())
        );
        Set<YetAnotherPathDTO> yetAnotherPathDTO= new HashSet<>();
        YetAnotherSerieDTO yetAnotherSerieDTO= new YetAnotherSerieDTO();
        for (Path path : flightSchedule.getPath()) {
            yetAnotherSerieDTO.setDurration(path.getSerie().getDurration());
            yetAnotherSerieDTO.setDestination(new AirportDTO(path.getSerie().getDestination().getIdarpt(),path.getSerie().getDestination().getName()));
            yetAnotherSerieDTO.setDeparture(new AirportDTO(path.getSerie().getDeparture().getIdarpt(),path.getSerie().getDeparture().getName()));

            YetAnotherPathDTO dto = new YetAnotherPathDTO(
                    path.getId(),path.getDeparture(),
                    path.getStopover(),
                    yetAnotherSerieDTO);
            yetAnotherPathDTO.add(dto);

        }
        this.setPath(yetAnotherPathDTO);

    }

}
