package com.example.pfe.assigned;

import com.example.pfe.flight_schedule.FlightSchedule;
import com.example.pfe.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AssignedRepository extends JpaRepository<Assigned, Integer> {

    Assigned findFirstByFlight(FlightSchedule flightSchedule);

    Assigned findFirstByPilot(User user);

    Assigned findFirstByCopilot(User user);

    Assigned findFirstByPnc(User user);

    Assigned findFirstByPnc2(User user);

    Assigned findFirstByPnc3(User user);

    boolean existsByFlightIdfs(Integer idfs);
    @Query("SELECT a FROM Assigned a WHERE a.pilot.id = :userId OR a.copilot.id = :userId OR a.pnc.id = :userId OR a.pnc2.id = :userId OR a.pnc3.id = :userId")
    List<Assigned> findByUserId(@Param("userId") Long userId);
}
