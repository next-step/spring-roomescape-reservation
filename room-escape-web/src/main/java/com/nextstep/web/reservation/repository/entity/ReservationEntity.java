package com.nextstep.web.reservation.repository.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationEntity {
    private Long id;
    private Long scheduleId;
    private LocalDateTime reservationTime;
    private String name;

    public ReservationEntity(Long id, Long scheduleId, LocalDateTime reservationTime, String name) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.reservationTime = reservationTime;
        this.name = name;
    }
}

