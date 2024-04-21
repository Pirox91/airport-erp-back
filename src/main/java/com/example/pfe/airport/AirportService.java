package com.example.pfe.airport;

import com.example.pfe.serie.Serie;
import com.example.pfe.serie.SerieRepository;
import com.example.pfe.util.NotFoundException;
import com.example.pfe.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AirportService {

    private final AirportRepository airportRepository;
    private final SerieRepository serieRepository;

    public AirportService(final AirportRepository airportRepository,
            final SerieRepository serieRepository) {
        this.airportRepository = airportRepository;
        this.serieRepository = serieRepository;
    }

    public List<AirportDTO> findAll() {
        final List<Airport> airports = airportRepository.findAll(Sort.by("idarpt"));
        return airports.stream()
                .map(airport -> mapToDTO(airport, new AirportDTO()))
                .toList();
    }

    public AirportDTO get(final Integer idarpt) {
        return airportRepository.findById(idarpt)
                .map(airport -> mapToDTO(airport, new AirportDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final AirportDTO airportDTO) {
        final Airport airport = new Airport();
        mapToEntity(airportDTO, airport);
        return airportRepository.save(airport).getIdarpt();
    }

    public void update(final Integer idarpt, final AirportDTO airportDTO) {
        final Airport airport = airportRepository.findById(idarpt)
                .orElseThrow(NotFoundException::new);
        mapToEntity(airportDTO, airport);
        airportRepository.save(airport);
    }

    public void delete(final Integer idarpt) {
        airportRepository.deleteById(idarpt);
    }

    private AirportDTO mapToDTO(final Airport airport, final AirportDTO airportDTO) {
        airportDTO.setIdarpt(airport.getIdarpt());
        airportDTO.setName(airport.getName());
        return airportDTO;
    }

    private Airport mapToEntity(final AirportDTO airportDTO, final Airport airport) {
        airport.setName(airportDTO.getName());
        return airport;
    }

    public ReferencedWarning getReferencedWarning(final Integer idarpt) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Airport airport = airportRepository.findById(idarpt)
                .orElseThrow(NotFoundException::new);
        final Serie departureSerie = serieRepository.findFirstByDeparture(airport);
        if (departureSerie != null) {
            referencedWarning.setKey("airport.serie.departure.referenced");
            referencedWarning.addParam(departureSerie.getIds());
            return referencedWarning;
        }
        final Serie destinationSerie = serieRepository.findFirstByDestination(airport);
        if (destinationSerie != null) {
            referencedWarning.setKey("airport.serie.destination.referenced");
            referencedWarning.addParam(destinationSerie.getIds());
            return referencedWarning;
        }
        return null;
    }

}
