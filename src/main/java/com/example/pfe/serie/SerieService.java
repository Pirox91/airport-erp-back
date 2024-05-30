package com.example.pfe.serie;

import com.example.pfe.airplane.Airplane;
import com.example.pfe.airplane.AirplaneRepository;
import com.example.pfe.airport.Airport;
import com.example.pfe.airport.AirportRepository;
import com.example.pfe.flight_schedule.FlightSchedule;
import com.example.pfe.flight_schedule.FlightScheduleRepository;
import com.example.pfe.path.Path;
import com.example.pfe.path.PathRepository;
import com.example.pfe.util.NotFoundException;
import com.example.pfe.util.ReferencedWarning;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SerieService {

    private final SerieRepository serieRepository;
    private final AirportRepository airportRepository;
    private final AirplaneRepository airplaneRepository;
    private final PathRepository pathRepository;
    private final FlightScheduleRepository flightScheduleRepository;

    public SerieService(final SerieRepository serieRepository,
            final AirportRepository airportRepository, final AirplaneRepository airplaneRepository,
            final PathRepository pathRepository,FlightScheduleRepository flightScheduleRepository) {
        this.serieRepository = serieRepository;
        this.airportRepository = airportRepository;
        this.airplaneRepository = airplaneRepository;
        this.pathRepository = pathRepository;
        this.flightScheduleRepository = flightScheduleRepository;
    }

    public List<SerieDTO> findAll() {
        final List<Serie> series = serieRepository.findAll(Sort.by("ids"));
        return series.stream()
                .map(serie -> mapToDTO(serie, new SerieDTO()))
                .toList();
    }

    public SerieDTO get(final Integer ids) {
        return serieRepository.findById(ids)
                .map(serie -> mapToDTO(serie, new SerieDTO()))
                .orElseThrow(NotFoundException::new);
    }
    public List<SerieDTO> getbyAirplane(final Integer ids) {
        final List<Serie> series = serieRepository.findByAirplaneIdap(ids);
        return series.stream()
                .map(serie -> mapToDTO(serie, new SerieDTO()))
                .toList();
    }

    public Integer create(final SerieDTO serieDTO) {
        final Serie serie = new Serie();
        mapToEntity(serieDTO, serie);
        return serieRepository.save(serie).getIds();
    }

    public void update(final Integer ids, final SerieDTO serieDTO) {
        final Serie serie = serieRepository.findById(ids)
                .orElseThrow(NotFoundException::new);
        mapToEntity(serieDTO, serie);
        serieRepository.save(serie);
    }

    public void delete(final Integer ids) {
        serieRepository.deleteById(ids);
    }

    private SerieDTO mapToDTO(final Serie serie, final SerieDTO serieDTO) {
        serieDTO.setIds(serie.getIds());
        serieDTO.setDurration(serie.getDurration());
        serieDTO.setDeparture(serie.getDeparture() == null ? null : serie.getDeparture().getIdarpt());
        serieDTO.setDestination(serie.getDestination() == null ? null : serie.getDestination().getIdarpt());
        serieDTO.setAirplane(serie.getAirplane() == null ? null : serie.getAirplane().getIdap());
        return serieDTO;
    }

    private Serie mapToEntity(final SerieDTO serieDTO, final Serie serie) {
        serie.setDurration(serieDTO.getDurration());
        final Airport departure = serieDTO.getDeparture() == null ? null : airportRepository.findById(serieDTO.getDeparture())
                .orElseThrow(() -> new NotFoundException("departure not found"));
        serie.setDeparture(departure);
        final Airport destination = serieDTO.getDestination() == null ? null : airportRepository.findById(serieDTO.getDestination())
                .orElseThrow(() -> new NotFoundException("destination not found"));
        serie.setDestination(destination);
        final Airplane airplane = serieDTO.getAirplane() == null ? null : airplaneRepository.findById(serieDTO.getAirplane())
                .orElseThrow(() -> new NotFoundException("airplane not found"));
        serie.setAirplane(airplane);
        return serie;
    }

    public ReferencedWarning getReferencedWarning(final Integer ids) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Serie serie = serieRepository.findById(ids)
                .orElseThrow(NotFoundException::new);
        final List<Path> seriePath = pathRepository.findBySerie(serie);
        final List<FlightSchedule> serieFlightSchedule = seriePath.stream()
                .map(flightScheduleRepository::findByPath)
                .toList();
        LocalDateTime now = LocalDateTime.now();
        boolean hasFutureDeparture = serieFlightSchedule.stream()
                .anyMatch(schedule -> schedule.getArrival().isAfter(now));

        if (hasFutureDeparture) {
            referencedWarning.setKey("a flight is schedeuelet in the future using this serie");
            return referencedWarning;
        }
        else{
          //  referencedWarning.setKey("pepepopo");
            //return referencedWarning;
            Optional<Serie> deletedmark=serieRepository.findById(10);
            if (deletedmark.isPresent()){Serie deletedserie= deletedmark.get();
            for (Path i : seriePath) {
                i.setSerie(deletedserie);
                System.out.println(i.getSerie());
                pathRepository.save(i);
            }}else{
                referencedWarning.setKey("please make a placeholder serie with ids=10 beofre attempting to delete a serie that is referenced");
                return referencedWarning;

            }
            return null;
            }
    }

}
