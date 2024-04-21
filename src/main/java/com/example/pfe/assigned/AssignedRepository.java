package com.example.pfe.assigned;

import com.example.pfe.flight_schedule.FlightSchedule;
import com.example.pfe.user.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AssignedRepository extends JpaRepository<Assigned, Integer> {

    Assigned findFirstByFlight(FlightSchedule flightSchedule);

    Assigned findFirstByPilot(User user);

    Assigned findFirstByCopilot(User user);

    Assigned findFirstByPnc(User user);

    Assigned findFirstByPnc2(User user);

    Assigned findFirstByPnc3(User user);

    boolean existsByFlightIdfs(Integer idfs);

}
