package com.nextstep.web.schedule.repository.entity;

import lombok.Getter;

@Getter
public class ScheduleEntity {
    private Long id;
    private Long themeId;
    private String date;
    private String time;

    public ScheduleEntity(Long id, Long themeId, String date, String time) {
        this.id = id;
        this.themeId = themeId;
        this.date = date;
        this.time = time;
    }
}
