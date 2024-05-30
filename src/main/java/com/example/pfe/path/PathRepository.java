package com.example.pfe.path;

import com.example.pfe.flight_schedule.FlightSchedule;
import com.example.pfe.serie.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PathRepository extends JpaRepository<Path, Integer> {

    Path findFirstByFlight(FlightSchedule flightSchedule);

    Path findFirstBySerie(Serie serie);
    List<Path> findBySerie(Serie serie);

}
