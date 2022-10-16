package nextstep.web.endpoint.reservation.service;

import nextstep.domain.reservation.model.Reservation;
import nextstep.domain.reservation.model.Reservations;
import nextstep.web.endpoint.reservation.request.ReservationCreateRequest;
import nextstep.web.endpoint.reservation.request.ReservationDeleteRequest;
import nextstep.web.endpoint.reservation.request.ReservationsSearchRequest;
import nextstep.web.endpoint.reservation.response.ReservationResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final Reservations reservations = new Reservations();

    public Long create(ReservationCreateRequest request) {
        Reservation reservation = reservations.create(request.getDate(), request.getTime(), request.getName());

        return reservation.getId();
    }

    public void cancelByDateAndTime(ReservationDeleteRequest request) {
        reservations.cancelByDateTime(request.getDate(), request.getTime());
    }

    public List<ReservationResponse> findAllByDate(ReservationsSearchRequest request) {
        List<Reservation> findReservations = reservations.findAllByDate(request.getDate());

        return ReservationResponse.fromList(findReservations);
    }
}
