package com.example.pfe.weekly;

import com.example.pfe.flight_schedule.FlightSchedule;
import com.example.pfe.flight_schedule.FlightScheduleRepository;
import com.example.pfe.util.NotFoundException;
import com.example.pfe.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class WeeklyService {

    private final WeeklyRepository weeklyRepository;
    private final FlightScheduleRepository flightScheduleRepository;

    public WeeklyService(final WeeklyRepository weeklyRepository,
            final FlightScheduleRepository flightScheduleRepository) {
        this.weeklyRepository = weeklyRepository;
        this.flightScheduleRepository = flightScheduleRepository;
    }

    public List<WeeklyDTO> findAll() {
        final List<Weekly> weeklies = weeklyRepository.findAll(Sort.by("id"));
        return weeklies.stream()
                .map(weekly -> mapToDTO(weekly, new WeeklyDTO()))
                .toList();
    }

    public WeeklyDTO get(final Integer id) {
        return weeklyRepository.findById(id)
                .map(weekly -> mapToDTO(weekly, new WeeklyDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final WeeklyDTO weeklyDTO) {
        final Weekly weekly = new Weekly();
        mapToEntity(weeklyDTO, weekly);
        return weeklyRepository.save(weekly).getId();
    }

    public void update(final Integer id, final WeeklyDTO weeklyDTO) {
        final Weekly weekly = weeklyRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(weeklyDTO, weekly);
        weeklyRepository.save(weekly);
    }

    public void delete(final Integer id) {
        weeklyRepository.deleteById(id);
    }

    private WeeklyDTO mapToDTO(final Weekly weekly, final WeeklyDTO weeklyDTO) {
        weeklyDTO.setId(weekly.getId());
        weeklyDTO.setName(weekly.getName());
        return weeklyDTO;
    }

    private Weekly mapToEntity(final WeeklyDTO weeklyDTO, final Weekly weekly) {
        weekly.setName(weeklyDTO.getName());
        return weekly;
    }

    public ReferencedWarning getReferencedWarning(final Integer id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Weekly weekly = weeklyRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final FlightSchedule weeklyFlightSchedule = flightScheduleRepository.findFirstByWeekly(weekly);
        if (weeklyFlightSchedule != null) {
            referencedWarning.setKey("weekly.flightSchedule.weekly.referenced");
            referencedWarning.addParam(weeklyFlightSchedule.getIdfs());
            return referencedWarning;
        }
        return null;
    }

}
