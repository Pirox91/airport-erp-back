package com.example.pfe.assigned;

import ch.qos.logback.classic.model.LoggerModel;
import com.example.pfe.flight_schedule.FlightSchedule;
import com.example.pfe.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Assigned {

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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "flight_id", unique = true)
    private FlightSchedule flight;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pilot_id")
    private User pilot;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "copilot_id")
    private User copilot;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pnc_id")
    private User pnc;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pnc2id")
    private User pnc2;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pnc3id")
    private User pnc3;

}
