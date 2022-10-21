package nextstep.web.service;

import nextstep.domain.reservation.model.Reservation;
import nextstep.domain.reservation.service.ReservationDomainService;
import nextstep.web.endpoint.reservation.request.ReservationCreateRequest;
import nextstep.web.endpoint.reservation.request.ReservationDeleteRequest;
import nextstep.web.endpoint.reservation.request.ReservationsSearchRequest;
import nextstep.web.endpoint.reservation.response.ReservationResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationDomainService reservationDomainService;

    public ReservationService(ReservationDomainService reservationDomainService) {
        this.reservationDomainService = reservationDomainService;
    }

    public Long create(ReservationCreateRequest request) {
        Reservation reservation = reservationDomainService.create(request.getScheduleId(), request.getDate(), request.getTime(),request.getName());

        return reservation.getId();
    }

    public void cancelByDateAndTime(ReservationDeleteRequest request) {
        reservationDomainService.cancelByDateTime(request.getDate(), request.getTime());
    }

    public List<ReservationResponse> findAllByDate(ReservationsSearchRequest request) {
        List<Reservation> findReservations = reservationDomainService.findAllByDate(request.getDate());

        return ReservationResponse.fromList(findReservations);
    }
}
