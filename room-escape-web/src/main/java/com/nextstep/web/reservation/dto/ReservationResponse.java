package com.nextstep.web.reservation.dto;

import com.nextstep.web.reservation.repository.entity.ReservationEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextsetp.domain.reservation.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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

    public static List<ReservationResponse> toList(List<Reservation> reservations) {
        List<ReservationResponse> reservationResponses = new ArrayList<>();
        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);
            reservationResponses.add(new ReservationResponse((long) i, reservation.getDate(),
                    reservation.getTime(), reservation.getName()));
        }

        return reservationResponses;
    }

    public static List<ReservationResponse> toListFromEntity(List<ReservationEntity> reservationEntities) {
        return reservationEntities.stream()
                .map(entity ->
                        new ReservationResponse(entity.getId(),
                                LocalDate.parse(entity.getDate()),
                                LocalTime.parse(entity.getTime()),
                                entity.getName()))
                .collect(Collectors.toList());
    }
}
