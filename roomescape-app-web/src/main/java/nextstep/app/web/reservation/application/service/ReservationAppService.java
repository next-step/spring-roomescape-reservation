package nextstep.app.web.reservation.application.service;

import nextstep.app.web.reservation.application.dto.CreateReservationCommand;
import nextstep.app.web.reservation.application.dto.CreateReservationResult;
import nextstep.app.web.reservation.application.dto.DeleteReservationCommand;
import nextstep.app.web.reservation.application.usecase.CreateReservationUseCase;
import nextstep.app.web.reservation.application.usecase.DeleteReservationUseCase;
import nextstep.domain.reservation.domain.service.ReservationDomainService;
import org.springframework.stereotype.Service;

@Service
public class ReservationAppService implements CreateReservationUseCase, DeleteReservationUseCase {
    private final ReservationDomainService reservationDomainService;

    public ReservationAppService(ReservationDomainService reservationDomainService) {
        this.reservationDomainService = reservationDomainService;
    }

    @Override
    public CreateReservationResult create(CreateReservationCommand command) {
        Long reservationId = reservationDomainService.create(command.reservationDateTime(), command.name());
        return new CreateReservationResult(reservationId);
    }

    @Override
    public void delete(DeleteReservationCommand command) {
        reservationDomainService.delete(command.date(), command.time());
    }
}
