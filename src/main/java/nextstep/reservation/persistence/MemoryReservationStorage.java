package nextstep.reservation.persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import nextstep.reservation.domain.Reservation;

public class MemoryReservationStorage implements ReservationStorage {

    private final List<Reservation> reservations = new ArrayList<>();

    /**
     * 리스트에서 예약 데이터가 저장된 인덱스를 반환
     */
    @Override
    public Long insert(Reservation reservation) {
        long index = reservations.size();
        reservations.add(reservation);
        return index;
    }

    @Override
    public List<Reservation> findBy(Long scheduleId, LocalDate date) {
        return reservations.stream()
            .filter(it -> it.getDate().isEqual(date))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Reservation> findBy(Long scheduleId, LocalDate date, LocalTime time) {
        LocalDateTime targetDateTime = LocalDateTime.of(date, time);
        return reservations.stream()
            .filter(it -> it.localDateTime().isEqual(targetDateTime))
            .findAny();
    }

    @Override
    public void deleteBy(Long scheduleId, LocalDate date, LocalTime time) {
        reservations.stream()
            .filter(it -> Objects.equals(it.getDate(), date) && Objects.equals(it.getTime(), time))
            .findFirst()
            .ifPresent(reservations::remove);
    }
}
