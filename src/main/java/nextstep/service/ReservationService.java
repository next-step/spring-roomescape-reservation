package nextstep.service;

import nextstep.domain.Reservation;
import nextstep.domain.ReservationRepository;
import nextstep.domain.Schedule;
import nextstep.domain.ScheduleRepository;
import nextstep.dto.ReservationCreateRequest;
import nextstep.dto.ReservationFindAllResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {
    public static final String DUPLICATE_RESERVATION_MESSAGE = "동시간대에 이미 예약이 존재합니다.";

    private final ReservationRepository reservations;
    private final ScheduleRepository schedules;

    public ReservationService(ReservationRepository reservations, ScheduleRepository schedules) {
        this.reservations = reservations;
        this.schedules = schedules;
    }

    public Long createReservation(ReservationCreateRequest reservationCreateRequest) {
        Long scheduleId = reservationCreateRequest.getScheduleId();
        checkReservationAvailable(scheduleId);

        Schedule schedule = schedules.findById(scheduleId);
        LocalDate date = schedule.getDate();
        LocalTime time = schedule.getTime();
        String name = reservationCreateRequest.getName();

        Reservation reservation = reservations.save(new Reservation(scheduleId, date, time, name));
        return reservation.getId();
    }

    private void checkReservationAvailable(Long scheduleId) {
        if (reservations.existsByScheduleId(scheduleId)) {
            throw new IllegalArgumentException(DUPLICATE_RESERVATION_MESSAGE);
        }
    }

    public ReservationFindAllResponse findAllReservations(String inputDate) {
        LocalDate date = parseDate(inputDate);

        List<Reservation> findReservations = reservations.findAllByDate(date);
        return ReservationFindAllResponse.from(findReservations);
    }

    public void deleteReservation(String inputDate, String inputTime) {
        LocalDate date = parseDate(inputDate);
        LocalTime time = parseTime(inputTime);

        reservations.deleteByDateAndTime(date, time);
    }

    private LocalDate parseDate(String date) {
        return LocalDate.parse(date);
    }

    private LocalTime parseTime(String time) {
        return LocalTime.parse(time + ":00");
    }
}
