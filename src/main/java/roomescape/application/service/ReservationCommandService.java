package roomescape.application.service;

import org.springframework.stereotype.Service;
import roomescape.application.service.command.CreateReservationCommand;
import roomescape.application.service.command.DeleteReservationCommand;
import roomescape.application.service.component.creator.ReservationCreator;
import roomescape.application.service.component.reader.ReservationTimeReader;
import roomescape.application.service.component.reader.ThemeReader;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservation.vo.ReservationName;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.domain.theme.Theme;
import roomescape.domain.theme.vo.ThemeId;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationCommandService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeReader reservationTimeReader;
    private final ThemeReader themeReader;
    private final ReservationCreator reservationCreator;


    public ReservationCommandService(
            ReservationRepository reservationRepository,
            ReservationTimeReader reservationTimeReader,
            ThemeReader themeReader,
            ReservationCreator reservationCreator
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeReader = reservationTimeReader;
        this.themeReader = themeReader;
        this.reservationCreator = reservationCreator;
    }

    public Reservation create(CreateReservationCommand command) {
        ReservationTime reservationTime = reservationTimeReader.readById(new ReservationTimeId(command.getReservationTimeId()));
        Theme theme = themeReader.readById(new ThemeId(command.getThemeId()));

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
