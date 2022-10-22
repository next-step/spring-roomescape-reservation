package nextstep.repository;

import nextstep.Reservation;

import java.util.List;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
    List<Reservation> findByDate(String date);
}
