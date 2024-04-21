package com.example.pfe.user;

import com.example.pfe.flight_schedule.FlightScheduleRepository;
import com.example.pfe.request.Request;
import com.example.pfe.request.RequestRepository;
import com.example.pfe.util.NotFoundException;
import com.example.pfe.util.ReferencedWarning;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final FlightScheduleRepository flightScheduleRepository;
    private final RequestRepository requestRepository;

    public UserService(final UserRepository userRepository,
            final FlightScheduleRepository flightScheduleRepository,
            final RequestRepository requestRepository) {
        this.userRepository = userRepository;
        this.flightScheduleRepository = flightScheduleRepository;
        this.requestRepository = requestRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Integer id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Integer id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Integer id) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side

        userRepository.delete(user);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setAvailable(user.getAvailable());
        userDTO.setValiditelicense(user.getValiditelicense());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setRole(user.getRole());
        userDTO.setType(user.getType());

        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setAvailable(userDTO.getAvailable());
        user.setValiditelicense(userDTO.getValiditelicense());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setRole(userDTO.getRole());
        user.setType(userDTO.getType());
        return user;
    }

    public ReferencedWarning getReferencedWarning(final Integer id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Request userRequest = requestRepository.findFirstByUser(user);
        if (userRequest != null) {
            referencedWarning.setKey("user.request.user.referenced");
            referencedWarning.addParam(userRequest.getId());
            return referencedWarning;
        }
        return null;
    }

}
