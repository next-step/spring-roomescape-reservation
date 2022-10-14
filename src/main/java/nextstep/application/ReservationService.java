package nextstep.application;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.domain.Reservation;
import nextstep.domain.ReservationRepository;
import nextstep.ui.request.ReservationCreateRequest;
import nextstep.ui.response.ReservationResponse;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationResponse create(ReservationCreateRequest request) {
        validateDuplicateDateTime(request.getDate(), request.getTime());

        Reservation reservation = new Reservation(
            request.getDate(),
            request.getTime(),
            request.getName()
        );

        return ReservationResponse.from(reservationRepository.save(reservation));
    }

    private void validateDuplicateDateTime(LocalDate date, LocalTime time) {
        if (reservationRepository.existsByDateTime(date, time)) {
            throw new IllegalArgumentException("날짜와 시간이 똑같은 예약이 이미 존재합니다.");
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
