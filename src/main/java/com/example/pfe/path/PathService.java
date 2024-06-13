package com.example.pfe.path;

import com.example.pfe.flight_schedule.FlightSchedule;
import com.example.pfe.flight_schedule.FlightScheduleRepository;
import com.example.pfe.serie.Serie;
import com.example.pfe.serie.SerieRepository;
import com.example.pfe.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PathService {

    private final PathRepository pathRepository;
    private final FlightScheduleRepository flightScheduleRepository;
    private final SerieRepository serieRepository;

    public PathService(final PathRepository pathRepository,
            final FlightScheduleRepository flightScheduleRepository,
            final SerieRepository serieRepository) {
        this.pathRepository = pathRepository;
        this.flightScheduleRepository = flightScheduleRepository;
        this.serieRepository = serieRepository;
    }

    public List<PathDTO> findAll() {
        final List<Path> paths = pathRepository.findAll(Sort.by("id"));
        return paths.stream()
                .map(path -> mapToDTO(path, new PathDTO()))
                .toList();
    }

    public PathDTO get(final Integer id) {
        return pathRepository.findById(id)
                .map(path -> mapToDTO(path, new PathDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final PathDTO pathDTO) {
        final Path path = new Path();
        mapToEntity(pathDTO, path);
        return pathRepository.save(path).getId();
    }

    public void update(final Integer id, final PathDTO pathDTO) {
        final Path path = pathRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(pathDTO, path);
        pathRepository.save(path);
    }

    public void delete(final Integer id) {

        pathRepository.deleteById(id);
    }

    private PathDTO mapToDTO(final Path path, final PathDTO pathDTO) {
        pathDTO.setId(path.getId());
        pathDTO.setDeparture(path.getDeparture());
        pathDTO.setStopover(path.getStopover());
        pathDTO.setFlight(path.getFlight() == null ? null : path.getFlight().getIdfs());
        pathDTO.setSerie(path.getSerie() == null ? null : path.getSerie().getIds());
        return pathDTO;
    }

    private Path mapToEntity(final PathDTO pathDTO, final Path path) {
        path.setDeparture(pathDTO.getDeparture());
        path.setStopover(pathDTO.getStopover());
        final FlightSchedule flight = pathDTO.getFlight() == null ? null : flightScheduleRepository.findById(pathDTO.getFlight())
                .orElseThrow(() -> new NotFoundException("flight not found"));
        path.setFlight(flight);
        final Serie serie = pathDTO.getSerie() == null ? null : serieRepository.findById(pathDTO.getSerie())
                .orElseThrow(() -> new NotFoundException("serie not found"));
        path.setSerie(serie);
        return path;
    }

}
