package com.example.pfe.airplane;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface AirplaneRepository extends JpaRepository<Airplane, Integer> {

    @Query(value =
            """
     WITH AvailableAirplanes AS (
     SELECT a.idap AS airplane_id, a.model AS airplane_model
     FROM airplane a
     LEFT JOIN flight_schedule fs ON a.idap = fs.airplane_id
         AND :time BETWEEN fs.departure AND fs.arrival
     WHERE fs.airplane_id IS NULL
 ),
 NextDepartures AS (
     SELECT fs.airplane_id, MIN(fs.departure) AS next_departure_time
     FROM flight_schedule fs
     WHERE fs.departure > :time
     GROUP BY fs.airplane_id
 )
 SELECT aa.airplane_id, aa.airplane_model, nd.next_departure_time
 FROM AvailableAirplanes aa
 LEFT JOIN NextDepartures nd ON aa.airplane_id = nd.airplane_id;
 """
            , nativeQuery = true)
    List<Object[]> findAvailableAirplanesWithNextDeparture(@Param("time") LocalDateTime time);}