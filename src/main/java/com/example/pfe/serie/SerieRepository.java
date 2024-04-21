package com.example.pfe.serie;

import com.example.pfe.airplane.Airplane;
import com.example.pfe.airport.Airport;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SerieRepository extends JpaRepository<Serie, Integer> {

    Serie findFirstByDeparture(Airport airport);

    Serie findFirstByDestination(Airport airport);

    Serie findFirstByAirplane(Airplane airplane);

}
