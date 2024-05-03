package com.example.pfe.airplane;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface AirplaneRepository extends JpaRepository<Airplane, Integer> {

    @Query(value = "SELECT a.idap as airplaneId, a.model as airplaneModel, MIN(fs.departure) as nextDepartureTime " +
            "FROM airplane a " +
            "LEFT JOIN flight_schedule fs ON a.idap = fs.airplane_id " +
            "WHERE :time NOT BETWEEN fs.departure AND fs.arrival " +
            "AND fs.departure > :time " +
            "GROUP BY a.idap, a.model", nativeQuery = true)
    List<Object[]> findAvailableAirplanesWithNextDeparture(@Param("time") LocalDateTime time);}
