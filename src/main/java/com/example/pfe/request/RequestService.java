package com.example.pfe.request;

import com.example.pfe.user.User;
import com.example.pfe.user.UserRepository;
import com.example.pfe.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    public RequestService(final RequestRepository requestRepository,
            final UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
    }

    public List<RequestDTO> findAll() {
        final List<Request> requests = requestRepository.findAll(Sort.by("id"));
        return requests.stream()
                .map(request -> mapToDTO(request, new RequestDTO()))
                .toList();
    }

    public RequestDTO get(final Integer id) {
        return requestRepository.findById(id)
                .map(request -> mapToDTO(request, new RequestDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final RequestDTO requestDTO) {
        final Request request = new Request();
        mapToEntity(requestDTO, request);
        return requestRepository.save(request).getId();
    }

    public void update(final Integer id, final RequestDTO requestDTO) {
        final Request request = requestRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(requestDTO, request);
        requestRepository.save(request);
    }
    public void updateViewed(final Integer id) {
        final Request request = requestRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        RequestDTO requestDTO= new RequestDTO();
        mapToDTO(request, requestDTO);
        requestDTO.setViewed(false);
        mapToEntity(requestDTO, request);
        requestRepository.save(request);
    }


    public void delete(final Integer id) {
        requestRepository.deleteById(id);
    }

    private RequestDTO mapToDTO(final Request request, final RequestDTO requestDTO) {
        requestDTO.setId(request.getId());
        requestDTO.setViewed(request.getViewed());
        requestDTO.setState(request.getState());
        requestDTO.setBody(request.getBody());
        requestDTO.setTitle(request.getTitle());
        requestDTO.setUser(request.getUser() == null ? null : request.getUser().getId());
        return requestDTO;
    }

    private Request mapToEntity(final RequestDTO requestDTO, final Request request) {
        request.setState(requestDTO.getState());
        request.setViewed(requestDTO.getViewed());
        request.setBody(requestDTO.getBody());
        request.setTitle(requestDTO.getTitle());
        final User user = requestDTO.getUser() == null ? null : userRepository.findById(requestDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        request.setUser(user);
        return request;
    }

}
