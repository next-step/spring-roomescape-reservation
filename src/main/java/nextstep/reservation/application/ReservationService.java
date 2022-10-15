package nextstep.reservation.application;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.reader.application.DateTimeReader;
import nextstep.reader.domain.DateTime;
import nextstep.reservation.domain.Reservation;
import nextstep.reservation.domain.ReservationRepository;
import nextstep.reservation.ui.request.ReservationCreateRequest;
import nextstep.reservation.ui.response.ReservationResponse;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final DateTimeReader dateTimeReader;

    public ReservationService(
        ReservationRepository reservationRepository,
        DateTimeReader dateTimeReader
    ) {
        this.reservationRepository = reservationRepository;
        this.dateTimeReader = dateTimeReader;
    }

    public ReservationResponse create(ReservationCreateRequest request) {
        validateAlreadyReservedSchedule(request.getScheduleId());
        DateTime datetime = dateTimeReader.getDateTimeByScheduleId(request.getScheduleId());
        Reservation reservation = reservationRepository.save(new Reservation(
            request.getScheduleId(),
            datetime.getDate(),
            datetime.getTime(),
            request.getName()
        ));
        return ReservationResponse.from(reservation);
    }

    private void validateAlreadyReservedSchedule(Long scheduleId) {
        if (reservationRepository.existsByScheduleId(scheduleId)) {
            throw new IllegalStateException("이미 해당 스케줄에 예약이 존재합니다.");
        }
    }

    public List<ReservationResponse> findAllByDate(LocalDate date) {
        return ReservationResponse.of(reservationRepository.findAllByDate(date));
    }

    public void deleteByDateTime(LocalDate date, LocalTime time) {
        int deletedCount = reservationRepository.deleteByDateTime(date, time);
        if (deletedCount == 0) {
            throw new IllegalArgumentException("시간과 날짜에 해당하는 예약정보가 없습니다.");
        }
    }
}
