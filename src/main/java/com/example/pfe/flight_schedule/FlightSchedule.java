package com.example.pfe.flight_schedule;

import com.example.pfe.airplane.Airplane;
import com.example.pfe.path.Path;
import com.example.pfe.weekly.Weekly;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class FlightSchedule {

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
    private Integer idfs;

    @Column(nullable = false)
    private LocalDateTime arrival;

    @Column(nullable = false)
    private LocalDateTime departure;
    private Boolean delay;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "airplane_id", nullable = false)
    private Airplane airplane;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "flight")
    private Set<Path> path;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "weekly_id")
    private Weekly weekly;

}
