package com.example.pfe.flight_schedule;

import com.example.pfe.airplane.Airplane;
import com.example.pfe.path.Path;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, Integer> {

    FlightSchedule findFirstByAirplane(Airplane airplane);
    FlightSchedule findByPath(Path path);

    @Query("SELECT fs FROM FlightSchedule fs  WHERE fs NOT IN (SELECT a.flight FROM Assigned a )")
    List<FlightSchedule> findUnreferencedFlightSchedules();
}
