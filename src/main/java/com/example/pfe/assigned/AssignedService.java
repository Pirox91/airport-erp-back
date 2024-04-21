package com.example.pfe.assigned;

import com.example.pfe.flight_schedule.FlightSchedule;
import com.example.pfe.flight_schedule.FlightScheduleRepository;
import com.example.pfe.user.User;
import com.example.pfe.user.UserRepository;
import com.example.pfe.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AssignedService {

    private final AssignedRepository assignedRepository;
    private final FlightScheduleRepository flightScheduleRepository;
    private final UserRepository userRepository;

    public AssignedService(final AssignedRepository assignedRepository,
            final FlightScheduleRepository flightScheduleRepository,
            final UserRepository userRepository) {
        this.assignedRepository = assignedRepository;
        this.flightScheduleRepository = flightScheduleRepository;
        this.userRepository = userRepository;
    }

    public List<AssignedDTO> findAll() {
        final List<Assigned> assigneds = assignedRepository.findAll(Sort.by("id"));
        return assigneds.stream()
                .map(assigned -> mapToDTO(assigned, new AssignedDTO()))
                .toList();
    }

    public AssignedDTO get(final Integer id) {
        return assignedRepository.findById(id)
                .map(assigned -> mapToDTO(assigned, new AssignedDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final AssignedDTO assignedDTO) {
        final Assigned assigned = new Assigned();
        mapToEntity(assignedDTO, assigned);
        return assignedRepository.save(assigned).getId();
    }

    public void update(final Integer id, final AssignedDTO assignedDTO) {
        final Assigned assigned = assignedRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(assignedDTO, assigned);
        assignedRepository.save(assigned);
    }

    public void delete(final Integer id) {
        assignedRepository.deleteById(id);
    }

    private AssignedDTO mapToDTO(final Assigned assigned, final AssignedDTO assignedDTO) {
        assignedDTO.setId(assigned.getId());
        assignedDTO.setFlight(assigned.getFlight() == null ? null : assigned.getFlight().getIdfs());
        assignedDTO.setPilot(assigned.getPilot() == null ? null : assigned.getPilot().getId());
        assignedDTO.setCopilot(assigned.getCopilot() == null ? null : assigned.getCopilot().getId());
        assignedDTO.setPnc(assigned.getPnc() == null ? null : assigned.getPnc().getId());
        assignedDTO.setPnc2(assigned.getPnc2() == null ? null : assigned.getPnc2().getId());
        assignedDTO.setPnc3(assigned.getPnc3() == null ? null : assigned.getPnc3().getId());
        return assignedDTO;
    }

    private Assigned mapToEntity(final AssignedDTO assignedDTO, final Assigned assigned) {
        final FlightSchedule flight = assignedDTO.getFlight() == null ? null : flightScheduleRepository.findById(assignedDTO.getFlight())
                .orElseThrow(() -> new NotFoundException("flight not found"));
        assigned.setFlight(flight);
        final User pilot = assignedDTO.getPilot() == null ? null : userRepository.findById(assignedDTO.getPilot())
                .orElseThrow(() -> new NotFoundException("pilot not found"));
        assigned.setPilot(pilot);
        final User copilot = assignedDTO.getCopilot() == null ? null : userRepository.findById(assignedDTO.getCopilot())
                .orElseThrow(() -> new NotFoundException("copilot not found"));
        assigned.setCopilot(copilot);
        final User pnc = assignedDTO.getPnc() == null ? null : userRepository.findById(assignedDTO.getPnc())
                .orElseThrow(() -> new NotFoundException("pnc not found"));
        assigned.setPnc(pnc);
        final User pnc2 = assignedDTO.getPnc2() == null ? null : userRepository.findById(assignedDTO.getPnc2())
                .orElseThrow(() -> new NotFoundException("pnc2 not found"));
        assigned.setPnc2(pnc2);
        final User pnc3 = assignedDTO.getPnc3() == null ? null : userRepository.findById(assignedDTO.getPnc3())
                .orElseThrow(() -> new NotFoundException("pnc3 not found"));
        assigned.setPnc3(pnc3);
        return assigned;
    }

    public boolean flightExists(final Integer idfs) {
        return assignedRepository.existsByFlightIdfs(idfs);
    }

}
