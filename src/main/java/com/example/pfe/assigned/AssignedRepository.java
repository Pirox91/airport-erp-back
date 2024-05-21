package com.example.pfe.assigned;

import com.example.pfe.flight_schedule.FlightSchedule;
import com.example.pfe.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
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
    @Query("SELECT u FROM User u " +
            "LEFT JOIN Assigned a ON u.id IN (a.pilot.id, a.copilot.id, a.pnc.id, a.pnc2.id, a.pnc3.id) " +
            "LEFT JOIN FlightSchedule fs ON a.flight.idfs = fs.idfs " +
            "WHERE fs.idfs IS NULL OR " +
            "((fs.departure < :t1 OR fs.departure > :t2) AND (fs.arrival < :t1 OR fs.arrival > :t2))")
    List<User> findAvailableUsersBetween(@Param("t1") LocalDateTime t1, @Param("t2") LocalDateTime t2);

}
