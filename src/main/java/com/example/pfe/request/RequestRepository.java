package com.example.pfe.request;

import com.example.pfe.user.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RequestRepository extends JpaRepository<Request, Integer> {

    Request findFirstByUser(User user);

}
