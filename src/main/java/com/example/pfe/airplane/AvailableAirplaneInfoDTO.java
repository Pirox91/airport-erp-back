package com.example.pfe.airplane;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter

public class AvailableAirplaneInfoDTO {
        private Integer airplaneId;
        private String airplaneModel;
        private LocalDateTime nextDepartureTime;
    public AvailableAirplaneInfoDTO(Integer airplaneId, String airplaneModel, LocalDateTime nextDepartureTime) {
        this.airplaneId = airplaneId;
        this.airplaneModel = airplaneModel;
        this.nextDepartureTime = nextDepartureTime;
    }
    }
