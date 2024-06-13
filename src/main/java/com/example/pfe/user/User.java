package com.example.pfe.user;

import com.example.pfe.flight_schedule.FlightSchedule;
import com.example.pfe.request.Request;
import com.example.pfe.token.Token;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer id;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private Boolean available;

    @Column
    private Boolean validitelicense;

    @Column
    private String email;

    @Column
    private String password;
    @Column
    private Integer passport;

    @Column(nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "user")
    private List<Token> tokens;

    @Column(nullable = false)
    private String surname;

    @Column
    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "user")
    private Set<Request> request;


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
}
