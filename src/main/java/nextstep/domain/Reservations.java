package nextstep.domain;

import nextstep.exception.ClientException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Reservations {
    private static final List<Reservation> reservations = new ArrayList<>();

    public static Long add(Reservation reservation) {
        if (isExisted(reservation)) {
            throw new ClientException("해당 날짜와 시간에 이미 예약이 존재합니다.");
        }

        reservations.add(reservation.withId());

        return (long) reservations.size();
    }

    public static boolean isExisted(Reservation reservation) {
        return reservations.stream()
            .anyMatch(it -> Objects.equals(it.getDate(), reservation.getDate()) && Objects.equals(it.getTime(), reservation.getTime()));
    }

    public static void removeBySchedule(String date, String time) {
        reservations.stream()
            .filter(it -> Objects.equals(it.getDate(), LocalDate.parse(date)) && Objects.equals(it.getTime(), LocalTime.parse(time)))
            .findFirst()
            .ifPresent(reservations::remove);
    }

    public static List<Reservation> findAllByDate(String date) {
        return reservations.stream()
            .filter(it -> it.getDate().isEqual(LocalDate.parse(date)))
            .collect(Collectors.toList());
    }
}
