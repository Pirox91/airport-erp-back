package com.example.pfe.serie;

import com.example.pfe.airplane.Airplane;
import com.example.pfe.airport.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface SerieRepository extends JpaRepository<Serie, Integer> {

    Serie findFirstByDeparture(Airport airport);

    Serie findFirstByDestination(Airport airport);

    Serie findFirstByAirplane(Airplane airplane);
    List<Serie> findByAirplaneIdap(Integer airplane);

}
