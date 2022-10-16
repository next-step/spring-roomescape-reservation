package nextstep.app.web.reservation.application.usecase;

import nextstep.app.web.reservation.application.dto.DeleteReservationCommand;

public interface DeleteReservationUseCase {
    void delete(DeleteReservationCommand command);
}
