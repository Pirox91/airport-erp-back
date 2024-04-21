package com.example.pfe.flight_schedule;

import com.example.pfe.airplane.Airplane;
import com.example.pfe.airplane.AirplaneRepository;
import com.example.pfe.assigned.Assigned;
import com.example.pfe.assigned.AssignedRepository;
import com.example.pfe.path.Path;
import com.example.pfe.path.PathRepository;
import com.example.pfe.util.NotFoundException;
import com.example.pfe.util.ReferencedWarning;
import com.example.pfe.weekly.Weekly;
import com.example.pfe.weekly.WeeklyRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class FlightScheduleService {

    private final FlightScheduleRepository flightScheduleRepository;
    private final AirplaneRepository airplaneRepository;
    private final WeeklyRepository weeklyRepository;
    private final PathRepository pathRepository;
    private final AssignedRepository assignedRepository;

    public FlightScheduleService(final FlightScheduleRepository flightScheduleRepository,
            final AirplaneRepository airplaneRepository, final WeeklyRepository weeklyRepository,
            final PathRepository pathRepository, final AssignedRepository assignedRepository) {
        this.flightScheduleRepository = flightScheduleRepository;
        this.airplaneRepository = airplaneRepository;
        this.weeklyRepository = weeklyRepository;
        this.pathRepository = pathRepository;
        this.assignedRepository = assignedRepository;
    }

    public List<FlightScheduleDTO> findAll() {
        final List<FlightSchedule> flightSchedules = flightScheduleRepository.findAll(Sort.by("idfs"));
        return flightSchedules.stream()
                .map(flightSchedule -> mapToDTO(flightSchedule, new FlightScheduleDTO()))
                .toList();
    }

    public FlightScheduleDTO get(final Integer idfs) {
        return flightScheduleRepository.findById(idfs)
                .map(flightSchedule -> mapToDTO(flightSchedule, new FlightScheduleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final FlightScheduleDTO flightScheduleDTO) {
        final FlightSchedule flightSchedule = new FlightSchedule();
        mapToEntity(flightScheduleDTO, flightSchedule);
        return flightScheduleRepository.save(flightSchedule).getIdfs();
    }

    public void update(final Integer idfs, final FlightScheduleDTO flightScheduleDTO) {
        final FlightSchedule flightSchedule = flightScheduleRepository.findById(idfs)
                .orElseThrow(NotFoundException::new);
        mapToEntity(flightScheduleDTO, flightSchedule);
        flightScheduleRepository.save(flightSchedule);
    }

    public void delete(final Integer idfs) {
        flightScheduleRepository.deleteById(idfs);
    }

    private FlightScheduleDTO mapToDTO(final FlightSchedule flightSchedule,
            final FlightScheduleDTO flightScheduleDTO) {
        flightScheduleDTO.setIdfs(flightSchedule.getIdfs());
        flightScheduleDTO.setArrival(flightSchedule.getArrival());
        flightScheduleDTO.setDeparture(flightSchedule.getDeparture());
        flightScheduleDTO.setAirplane(flightSchedule.getAirplane() == null ? null : flightSchedule.getAirplane().getIdap());
        flightScheduleDTO.setWeekly(flightSchedule.getWeekly() == null ? null : flightSchedule.getWeekly().getId());
        return flightScheduleDTO;
    }

    private FlightSchedule mapToEntity(final FlightScheduleDTO flightScheduleDTO,
            final FlightSchedule flightSchedule) {
        flightSchedule.setArrival(flightScheduleDTO.getArrival());
        flightSchedule.setDeparture(flightScheduleDTO.getDeparture());
        final Airplane airplane = flightScheduleDTO.getAirplane() == null ? null : airplaneRepository.findById(flightScheduleDTO.getAirplane())
                .orElseThrow(() -> new NotFoundException("airplane not found"));
        flightSchedule.setAirplane(airplane);
        final Weekly weekly = flightScheduleDTO.getWeekly() == null ? null : weeklyRepository.findById(flightScheduleDTO.getWeekly())
                .orElseThrow(() -> new NotFoundException("weekly not found"));
        flightSchedule.setWeekly(weekly);
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
