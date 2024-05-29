package com.example.pfe.user;

import com.example.pfe.assigned.Assigned;
import com.example.pfe.assigned.AssignedRepository;
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
    private final RequestRepository requestRepository;
    private final AssignedRepository assignedRepository;

    public UserService(final UserRepository userRepository,
                       final RequestRepository requestRepository,
                       final AssignedRepository assignedRepository) {
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
        this.assignedRepository = assignedRepository;
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
  public UserDTO getbymail(final String mail) {
        return userRepository.findByEmail(mail)
                .map(user -> mapToDTO( (User) user, new UserDTO()))
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

        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setRole(user.getRole());
        userDTO.setType(user.getType());
        userDTO.setBirthday(user.getBirthday());

        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setAvailable(userDTO.getAvailable());
        user.setBirthday(userDTO.getBirthday());
        user.setValiditelicense(userDTO.getValiditelicense());
        user.setEmail(userDTO.getEmail());
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
        final Assigned pilotAssigned = assignedRepository.findFirstByPilot(user);
        if (pilotAssigned != null) {
            referencedWarning.setKey("user.assigned.pilot.referenced");
            referencedWarning.addParam(pilotAssigned.getId());
            return referencedWarning;
        }
        final Assigned copilotAssigned = assignedRepository.findFirstByCopilot(user);
        if (copilotAssigned != null) {
            referencedWarning.setKey("user.assigned.copilot.referenced");
            referencedWarning.addParam(copilotAssigned.getId());
            return referencedWarning;
        }
        final Assigned pncAssigned = assignedRepository.findFirstByPnc(user);
        if (pncAssigned != null) {
            referencedWarning.setKey("user.assigned.pnc.referenced");
            referencedWarning.addParam(pncAssigned.getId());
            return referencedWarning;
        }
        final Assigned pnc2Assigned = assignedRepository.findFirstByPnc2(user);
        if (pnc2Assigned != null) {
            referencedWarning.setKey("user.assigned.pnc2.referenced");
            referencedWarning.addParam(pnc2Assigned.getId());
            return referencedWarning;
        }
        final Assigned pnc3Assigned = assignedRepository.findFirstByPnc3(user);
        if (pnc3Assigned != null) {
            referencedWarning.setKey("user.assigned.pnc3.referenced");
            referencedWarning.addParam(pnc3Assigned.getId());
            return referencedWarning;
        }
        return null;
    }

}