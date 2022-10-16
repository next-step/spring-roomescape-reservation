package nextstep.app.web.reservation.application.usecase;

import nextstep.app.web.reservation.application.dto.CreateReservationCommand;
import nextstep.app.web.reservation.application.dto.CreateReservationResult;

public interface CreateReservationUseCase {
    CreateReservationResult create(CreateReservationCommand command);
}
