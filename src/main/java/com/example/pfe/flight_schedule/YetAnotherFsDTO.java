package com.example.pfe.flight_schedule;

import com.example.pfe.airplane.AirplaneDTO;
import com.example.pfe.airport.AirportDTO;
import com.example.pfe.path.Path;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
public class YetAnotherFsDTO {

    private Integer idfs;

    private LocalDateTime arrival;

    private LocalDateTime departure;

    private AirplaneDTO airplane;
    private Boolean delay;
    private Set<YetAnotherPathDTO> path;
    public void constru(FlightSchedule flightSchedule){
        this.setDelay(flightSchedule.getDelay());
        this.setIdfs(flightSchedule.getIdfs());
        this.setArrival(flightSchedule.getArrival());
        this.setDeparture(flightSchedule.getDeparture());
        this.setAirplane(flightSchedule.getAirplane() == null ? null :new AirplaneDTO(
                flightSchedule.getAirplane().getIdap(),
                flightSchedule.getAirplane().getAvailable(),
                flightSchedule.getAirplane().getModel(),
                flightSchedule.getAirplane().getName())
        );
        Set<YetAnotherPathDTO> yetAnotherPathDTO = new TreeSet<>(Comparator.comparing(YetAnotherPathDTO::getId));
        for (Path path : flightSchedule.getPath()) {
            YetAnotherSerieDTO yetAnotherSerieDTO= new YetAnotherSerieDTO();

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
