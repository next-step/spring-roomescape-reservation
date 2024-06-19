package roomescape.reservation.application;

import org.springframework.stereotype.Service;
import roomescape.reservationtime.application.ReservationTimeValidator;
import roomescape.exception.BadRequestException;
import roomescape.reservation.domain.ReservationRepository;
import roomescape.reservation.ui.dto.ReservationRequest;
import roomescape.theme.application.ThemeValidator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Service
public class ReservationValidator {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeValidator reservationTimeValidator;
    private final ThemeValidator themeValidator;

    public ReservationValidator(
            ReservationRepository reservationRepository,
            ReservationTimeValidator reservationTimeValidator,
            ThemeValidator themeValidator) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeValidator = reservationTimeValidator;
        this.themeValidator = themeValidator;
    }

    public void validateRequest(ReservationRequest reservationRequest) {
        String date = reservationRequest.getDate();
        Long timeId = reservationRequest.getTimeId();
        Long themeId = reservationRequest.getThemeId();

        checkDate(date);
        reservationTimeValidator.checkPresent(timeId);
        themeValidator.checkPresent(themeId);
        checkDuplicated(date, timeId, themeId);
    }

    private void checkDate(String date) {
        LocalDate reservationDate;
        LocalDate now = LocalDate.now();

        try {
            reservationDate = LocalDate.parse(date);
        }
        catch (DateTimeParseException exception) {
            throw new BadRequestException("유효하지 않은 날짜 형식입니다.");
        }
        if (!reservationDate.isAfter(now)) {
            throw new BadRequestException("해당 날짜는 예약이 불가능합니다.");
        }
    }

    private void checkDuplicated(String date, Long timeId, Long themeId) {
        if (reservationRepository.countMatchWith(date, timeId, themeId) > 0) {
            throw new BadRequestException("해당 방탈출은 이미 예약되었습니다.");
        }
    }
}
