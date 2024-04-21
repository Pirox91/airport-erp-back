package com.example.pfe.flight_schedule;

import com.example.pfe.airplane.Airplane;
import com.example.pfe.weekly.Weekly;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, Integer> {

    FlightSchedule findFirstByAirplane(Airplane airplane);

    FlightSchedule findFirstByWeekly(Weekly weekly);

}
