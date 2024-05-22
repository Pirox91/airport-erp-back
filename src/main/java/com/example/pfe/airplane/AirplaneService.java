package com.example.pfe.airplane;

import com.example.pfe.flight_schedule.FlightSchedule;
import com.example.pfe.flight_schedule.FlightScheduleRepository;
import com.example.pfe.serie.Serie;
import com.example.pfe.serie.SerieRepository;
import com.example.pfe.util.NotFoundException;
import com.example.pfe.util.ReferencedWarning;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AirplaneService {

    private final AirplaneRepository airplaneRepository;
    private final FlightScheduleRepository flightScheduleRepository;
    private final SerieRepository serieRepository;

    public AirplaneService(final AirplaneRepository airplaneRepository,
            final FlightScheduleRepository flightScheduleRepository,
            final SerieRepository serieRepository) {
        this.airplaneRepository = airplaneRepository;
        this.flightScheduleRepository = flightScheduleRepository;
        this.serieRepository = serieRepository;
    }

    public List<AirplaneDTO> findAll() {
        final List<Airplane> airplanes = airplaneRepository.findAll(Sort.by("idap"));
        return airplanes.stream()
                .map(airplane -> mapToDTO(airplane, new AirplaneDTO()))
                .toList();
    }

    public AirplaneDTO get(final Integer idap) {
        return airplaneRepository.findById(idap)
                .map(airplane -> mapToDTO(airplane, new AirplaneDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final AirplaneDTO airplaneDTO) {
        final Airplane airplane = new Airplane();
        mapToEntity(airplaneDTO, airplane);
        return airplaneRepository.save(airplane).getIdap();
    }

    public void update(final Integer idap, final AirplaneDTO airplaneDTO) {
        final Airplane airplane = airplaneRepository.findById(idap)
                .orElseThrow(NotFoundException::new);
        mapToEntity(airplaneDTO, airplane);
        airplaneRepository.save(airplane);
    }

    public void delete(final Integer idap) {
        airplaneRepository.deleteById(idap);
    }

    private AirplaneDTO mapToDTO(final Airplane airplane, final AirplaneDTO airplaneDTO) {
        airplaneDTO.setIdap(airplane.getIdap());
        airplaneDTO.setAvailable(airplane.getAvailable());
        airplaneDTO.setModel(airplane.getModel());
        airplaneDTO.setName(airplane.getName());
        return airplaneDTO;
    }

    private Airplane mapToEntity(final AirplaneDTO airplaneDTO, final Airplane airplane) {
        airplane.setAvailable(airplaneDTO.getAvailable());
        airplane.setModel(airplaneDTO.getModel());
        airplane.setIdap(airplaneDTO.getIdap());
        airplane.setName(airplaneDTO.getName());
    return airplane;
    }

    public ReferencedWarning getReferencedWarning(final Integer idap) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Airplane airplane = airplaneRepository.findById(idap)
                .orElseThrow(NotFoundException::new);
        final FlightSchedule airplaneFlightSchedule = flightScheduleRepository.findFirstByAirplane(airplane);
        if (airplaneFlightSchedule != null) {
            referencedWarning.setKey("airplane.flightSchedule.airplane.referenced");
            referencedWarning.addParam(airplaneFlightSchedule.getIdfs());
            return referencedWarning;
        }
        final Serie airplaneSerie = serieRepository.findFirstByAirplane(airplane);
        if (airplaneSerie != null) {
            referencedWarning.setKey("airplane.serie.airplane.referenced");
            referencedWarning.addParam(airplaneSerie.getIds());
            return referencedWarning;
        }
        return null;
    }
    public List<Object[]> getAvailableAirplanesWithNextDeparture(LocalDateTime time) {

        return airplaneRepository.findAvailableAirplanesWithNextDeparture(time);
    }

}
