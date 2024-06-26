package com.example.pfe.flight_schedule;

import com.example.pfe.airplane.Airplane;
import com.example.pfe.airplane.AirplaneDTO;
import com.example.pfe.airplane.AirplaneRepository;
import com.example.pfe.airport.AirportDTO;
import com.example.pfe.assigned.Assigned;
import com.example.pfe.assigned.AssignedRepository;
import com.example.pfe.path.Path;
import com.example.pfe.path.PathRepository;
import com.example.pfe.path.PathResource;
import com.example.pfe.util.NotFoundException;
import com.example.pfe.util.ReferencedWarning;


import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;


@Service
public class FlightScheduleService {

    private final FlightScheduleRepository flightScheduleRepository;
    private final AirplaneRepository airplaneRepository;
    private final PathRepository pathRepository;
    private final AssignedRepository assignedRepository;

    public FlightScheduleService(final FlightScheduleRepository flightScheduleRepository,
                                 final AirplaneRepository airplaneRepository,
                                 final PathRepository pathRepository, final AssignedRepository assignedRepository, PathResource pathResource) {
        this.flightScheduleRepository = flightScheduleRepository;
        this.airplaneRepository = airplaneRepository;
        this.pathRepository = pathRepository;
        this.assignedRepository = assignedRepository;
    }

    public List<YetAnotherFsDTO> findAll() {
        final List<FlightSchedule> flightSchedules = flightScheduleRepository.findAll();
        return flightSchedules.stream()
                .map(flightSchedule -> mapToAnotherDTO(flightSchedule, new YetAnotherFsDTO()))
                .toList();
    }
    public List<YetAnotherFsDTO> findunreferenced() {
        final List<FlightSchedule> flightSchedules = flightScheduleRepository.findUnreferencedFlightSchedules();
        return flightSchedules.stream()
                .map(flightSchedule -> mapToAnotherDTO(flightSchedule, new YetAnotherFsDTO()))
                .toList();
    }

    public YetAnotherFsDTO get(final Integer idfs) {
        return flightScheduleRepository.findById(idfs)
                .map(flightSchedule -> mapToAnotherDTO(flightSchedule, new YetAnotherFsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final FlightScheduleDTO flightScheduleDTO) {
        final FlightSchedule flightSchedule = new FlightSchedule();
        flightSchedule.setDelay(false);
        mapToEntity(flightScheduleDTO, flightSchedule);
        return flightScheduleRepository.save(flightSchedule).getIdfs();
    }

    public void updateDelayed(final Integer idfs) {
        final FlightSchedule flightSchedule = flightScheduleRepository.findById(idfs)
                .orElseThrow(NotFoundException::new);
        FlightScheduleDTO flightScheduleDTO= new FlightScheduleDTO();
        mapToDTO(flightSchedule, flightScheduleDTO);
        flightScheduleDTO.setDelay(!flightScheduleDTO.getDelay());
        mapToEntity(flightScheduleDTO, flightSchedule);
        flightScheduleRepository.save(flightSchedule);
    }

    public void delete(final Integer idfs) {
        flightScheduleRepository.deleteById(idfs);

    }
    public void update(final Integer idfs, final FlightScheduleDTO flightScheduleDTO) {
        if (!(assignedRepository.existsByFlightIdfs(idfs))) {
            final FlightSchedule flightSchedule = flightScheduleRepository.findById(idfs)
                    .orElseThrow(NotFoundException::new);
            mapToEntity(flightScheduleDTO, flightSchedule);
            flightScheduleRepository.save(flightSchedule); }
    }

    public FlightScheduleDTO mapToDTO(final FlightSchedule flightSchedule,
            final FlightScheduleDTO flightScheduleDTO) {
        flightScheduleDTO.setIdfs(flightSchedule.getIdfs());
        flightScheduleDTO.setArrival(flightSchedule.getArrival());
        flightScheduleDTO.setDeparture(flightSchedule.getDeparture());
        flightScheduleDTO.setAirplane(flightSchedule.getAirplane() == null ? null : flightSchedule.getAirplane().getIdap());
        return flightScheduleDTO;
    }
    public YetAnotherFsDTO mapToAnotherDTO(final FlightSchedule flightSchedule,
                                           final YetAnotherFsDTO flightScheduleDTO) {
        flightScheduleDTO.setDelay(flightSchedule.getDelay());
        flightScheduleDTO.setIdfs(flightSchedule.getIdfs());
        flightScheduleDTO.setArrival(flightSchedule.getArrival());
        flightScheduleDTO.setDeparture(flightSchedule.getDeparture());
        flightScheduleDTO.setAirplane(flightSchedule.getAirplane() == null ? null : new AirplaneDTO(
                flightSchedule.getAirplane().getIdap(),
                flightSchedule.getAirplane().getAvailable(),
                flightSchedule.getAirplane().getName(),
                flightSchedule.getAirplane().getModel())
        );
        Set<YetAnotherPathDTO> yetAnotherPathDTO = new TreeSet<>(Comparator.comparing(YetAnotherPathDTO::getId));
        for (Path path : flightSchedule.getPath()) {
            YetAnotherSerieDTO yetAnotherSerieDTO = new YetAnotherSerieDTO();
            yetAnotherSerieDTO.setIds(path.getSerie().getIds());
            yetAnotherSerieDTO.setDurration(path.getSerie().getDurration());
            yetAnotherSerieDTO.setDestination(new AirportDTO(path.getSerie().getDestination().getIdarpt(),path.getSerie().getDestination().getName(),path.getSerie().getDestination().getCountry(),path.getSerie().getDestination().getCity()));
            yetAnotherSerieDTO.setDeparture(new AirportDTO(path.getSerie().getDeparture().getIdarpt(),path.getSerie().getDeparture().getName(),path.getSerie().getDeparture().getCountry(),path.getSerie().getDeparture().getCity()));

            YetAnotherPathDTO dto = new YetAnotherPathDTO(
                    path.getId(), path.getDeparture(),
                    path.getStopover(),
                    yetAnotherSerieDTO
            );


            yetAnotherPathDTO.add(dto);
        }

        flightScheduleDTO.setPath(yetAnotherPathDTO);
        return flightScheduleDTO;
    }





    private FlightSchedule mapToEntity(final FlightScheduleDTO flightScheduleDTO,
            final FlightSchedule flightSchedule) {
        flightSchedule.setDelay(flightScheduleDTO.getDelay());
        flightSchedule.setArrival(flightScheduleDTO.getArrival());
        flightSchedule.setDeparture(flightScheduleDTO.getDeparture());

        final Airplane airplane = flightScheduleDTO.getAirplane() == null ? null : airplaneRepository.findById(flightScheduleDTO.getAirplane())
                .orElseThrow(() -> new NotFoundException("airplane not found"));
        flightSchedule.setAirplane(airplane);

        return flightSchedule;
    }

    public ReferencedWarning getReferencedWarning(final Integer idfs) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final FlightSchedule flightSchedule = flightScheduleRepository.findById(idfs)
                .orElseThrow(NotFoundException::new);
        final Path flightPath = pathRepository.findFirstByFlight(flightSchedule);
        if (flightPath != null) {
            referencedWarning.setKey("flightSchedule.path.flight.referenced");
            referencedWarning.addParam(flightPath.getId());
            return referencedWarning;
        }
        final Assigned flightAssigned = assignedRepository.findFirstByFlight(flightSchedule);
        if (flightAssigned != null) {
            referencedWarning.setKey("flightSchedule.assigned.flight.referenced");
            referencedWarning.addParam(flightAssigned.getId());
            return referencedWarning;
        }
        return null;
    }


}
