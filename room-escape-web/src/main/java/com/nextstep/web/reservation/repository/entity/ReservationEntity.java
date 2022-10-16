package com.nextstep.web.reservation.repository.entity;

import lombok.Getter;

@Getter
public class ReservationEntity {
    private Long id;
    private String date;
    private String time;
    private String name;

    public ReservationEntity(Long id, String date, String time, String name) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
    }
}

