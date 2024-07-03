package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.exception.ReservationDateInvalidException;
import roomescape.exception.ReservationDatePastException;
import roomescape.exception.ReservationDuplicateException;
import roomescape.exception.ReservationNameInvalidException;
import roomescape.model.*;
import roomescape.respository.ReservationDAO;
import roomescape.respository.ReservationTimeDAO;
import roomescape.respository.ThemeDAO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ReservationAdminService {
    private final ReservationDAO reservationDAO;
    private final ReservationTimeDAO reservationTimeDAO;
    private final ThemeDAO themeDAO;

    public ReservationAdminService(ReservationDAO reservationDAO, ReservationTimeDAO reservationTimeDAO,
                                   ThemeDAO themeDAO) {
        this.reservationDAO = reservationDAO;
        this.reservationTimeDAO = reservationTimeDAO;
        this.themeDAO = themeDAO;
    }

    private static boolean isInValidDateFormat(String date) {
        String DATE_PATTERN = "^(\\d{4})-(\\d{2})-(\\d{2})$";
        Pattern pattern = Pattern.compile(DATE_PATTERN);
        Matcher matcher = pattern.matcher(date);
        return !matcher.matches();
    }

    private boolean isPastDate(String date) {
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate reservationDate = LocalDate.parse(date, DATE_FORMATTER);
        LocalDate today = LocalDate.now();
        return reservationDate.isBefore(today);
    }

    public ReservationCreateResponseDto createReservation(ReservationCreateDto reservationCreateDto) {
        validateReservationCreation(reservationCreateDto);
        ReservationTime reservationTime = reservationTimeDAO.findReservationTimeById(reservationCreateDto.getTimeId());
        Theme theme = themeDAO.findThemeById(reservationCreateDto.getThemeId());
        return reservationDAO.insertReservation(reservationCreateDto, reservationTime.getId(), theme.getId());
    }

    private void validateReservationCreation(ReservationCreateDto reservationCreateDto) {
        if (isInValidDateFormat(reservationCreateDto.getDate())) {
            throw new ReservationDateInvalidException("yyyy-mm-dd 형식에 맞는 날짜를 입력하세요");
        }
        if (isPastDate(reservationCreateDto.getDate())) {
            throw new ReservationDatePastException("예약은 미래 시간에만 생성 가능합니다.");
        }
        if (containsSpecialCharacters(reservationCreateDto.getName()) || containsWhitespace(reservationCreateDto.getName())) {
            throw new ReservationNameInvalidException("이름에 특수문자나 공백이 들어갈 수 없습니다.");
        }
        if (isSameReservationExists(reservationCreateDto)) {
            throw new ReservationDuplicateException("동일한 예약이 존재합니다.");
        }
    }

    private boolean isSameReservationExists(ReservationCreateDto reservationCreateDto) {
        return reservationDAO.isReservationExistByTimeIdAndThemeIdAndDate(reservationCreateDto.getTimeId(),
                reservationCreateDto.getThemeId(), reservationCreateDto.getDate());
    }

    private boolean containsSpecialCharacters(String input) {
        String SPECIAL_CHAR_PATTERN = "[^\\p{IsAlphabetic}\\p{IsDigit} ]";
        Pattern pattern = Pattern.compile(SPECIAL_CHAR_PATTERN);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    private boolean containsWhitespace(String input) {
        String WHITESPACE_PATTERN = "\\s";
        Pattern pattern = Pattern.compile(WHITESPACE_PATTERN);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public List<Reservation> getReservations() {
        return reservationDAO.readReservations();
    }

    public void deleteReservation(Long id) {
        reservationDAO.deleteReservation(id);
    }
}
