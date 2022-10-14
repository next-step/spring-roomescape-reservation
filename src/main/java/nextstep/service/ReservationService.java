package nextstep.service;

import java.util.ArrayList;
import java.util.List;
import nextstep.controller.dto.ReservationRequest;
import nextstep.controller.dto.ReservationResponse;
import nextstep.domain.Reservation;
import nextstep.domain.Reservations;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final Reservations reservations = new Reservations(new ArrayList<>());

    public Integer make(ReservationRequest request) {
        return reservations.make(request.getDate(), request.getTime(), request.getName());
    }

    public List<ReservationResponse> check(String date) {
        List<Reservation> checkedReservations = reservations.check(date);

        List<ReservationResponse> responses = new ArrayList<>();

        Integer id = 1;
        for (Reservation reservation : checkedReservations) {
            ReservationResponse response = getResponse(id, reservation);
            responses.add(response);
            id++;
        }

        return responses;
    }

    private ReservationResponse getResponse(Integer id, Reservation reservation) {
        return new ReservationResponse(
            id,
            reservation.getDate().toString(),
            reservation.getTime().toString(),
            reservation.getName()
        );
    }

    public void cancel(String date, String time) {
        reservations.cancel(date, time);
    }
}
