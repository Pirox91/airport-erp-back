package com.example.pfe.path;

import com.example.pfe.flight_schedule.FlightSchedule;
import com.example.pfe.serie.Serie;
import jakarta.persistence.*;

import java.sql.Time;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Getter
@Setter
public class Path {

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

    @Column(nullable = false)
    private LocalDateTime departure;

    @Column(nullable = false)
    private Time stopover;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", nullable = false)
    private FlightSchedule flight;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "serie_id", nullable = true)
    private Serie serie;
    public void nullifyforeign(){
        this.serie=null;

    }
}
