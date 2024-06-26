package roomescape.application.service;

import org.springframework.stereotype.Service;
import roomescape.application.service.command.CreateReservationCommand;
import roomescape.application.service.command.DeleteReservationCommand;
import roomescape.application.service.component.creator.ReservationCreator;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationRepository;
import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservation.vo.ReservationName;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.reservationtime.ReservationTimeRepository;
import roomescape.domain.theme.Theme;
import roomescape.domain.theme.ThemeRepository;

@Service
public class ReservationCommandService {

    private final ReservationRepository reservationRepository;

    private final ReservationTimeRepository reservationTimeRepository;

    private final ThemeRepository themeRepository;

    private final ReservationCreator reservationCreator;


    public ReservationCommandService(
            ReservationRepository reservationRepository,
            ReservationTimeRepository reservationTimeRepository,
            ThemeRepository themeRepository,
            ReservationCreator reservationCreator
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
        this.themeRepository = themeRepository;
        this.reservationCreator = reservationCreator;
    }

    public Reservation create(CreateReservationCommand command) {
        ReservationTime reservationTime = reservationTimeRepository.findById(command.getReservationTimeId());
        Theme theme = themeRepository.findById(command.getThemeId());

        return reservationCreator.create(
                new ReservationName(command.getReservationName()),
                new ReservationDate(command.getReservationDate()),
                reservationTime,
                theme
        );
    }

    public void delete(DeleteReservationCommand command) {
        reservationRepository.delete(command.getReservationId());
    }
}
