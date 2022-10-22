package com.nextstep.web.reservation.dto;

import com.nextstep.web.reservation.repository.entity.ReservationEntity;
import com.nextstep.web.schedule.repository.entity.ScheduleEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ReservationResponse {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;

    private ReservationResponse(Long id, LocalDate date, LocalTime time, String name) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public static List<ReservationResponse> toListFromEntity(List<ReservationEntity> reservationEntities, List<ScheduleEntity> scheduleEntities) {
        final Map<Long, ScheduleEntity> scheduleMap = scheduleEntities.stream()
                .collect(Collectors.toMap(ScheduleEntity::getId, Function.identity()));

        return reservationEntities.stream()
                .map(entity ->
                        new ReservationResponse(entity.getId(),
                                LocalDate.parse(scheduleMap.get(entity.getScheduleId()).getDate()),
                                LocalTime.parse(scheduleMap.get(entity.getScheduleId()).getTime()),
                                entity.getName()))
                .collect(Collectors.toList());
    }
}
