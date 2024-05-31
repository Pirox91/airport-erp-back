package com.example.pfe.auth;

import com.example.pfe.assigned.AssignedDTO;
import com.example.pfe.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@EnableMethodSecurity
public class AuthenticationController {
    private final AuthenticationService service;
    private final EmailService mailer;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
       mailer.sendEmail(request.getEmail(), "[Welcome Abroad - TunisairExpress]", request.getEmail()+' '+request.getPassword());
        return ResponseEntity.ok(service.register(request));
    }
  @CrossOrigin( origins = { "http://localhost:4200"})

          @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {

        return ResponseEntity.ok(service.authenticate(request));
    }
}