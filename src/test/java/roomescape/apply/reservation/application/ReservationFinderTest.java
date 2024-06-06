package roomescape.apply.reservation.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import roomescape.apply.reservation.domain.repository.ReservationJDBCRepository;
import roomescape.apply.reservation.domain.repository.ReservationRepository;
import roomescape.apply.reservation.ui.dto.ReservationResponse;
import roomescape.apply.time.domain.ReservationTime;
import roomescape.apply.time.domain.repository.ReservationTimeJDBCRepository;
import roomescape.apply.time.domain.repository.ReservationTimeRepository;
import roomescape.support.BaseTestService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static roomescape.support.ReservationsFixture.reservation;
import static roomescape.support.ReservationsFixture.reservationTime;

class ReservationFinderTest extends BaseTestService {

    private ReservationFinder reservationFinder;
    private ReservationRepository reservationRepository;
    private ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void setUp() {
        transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        reservationRepository = new ReservationJDBCRepository(template);
        reservationTimeRepository = new ReservationTimeJDBCRepository(template);
        reservationFinder = new ReservationFinder(reservationRepository);
    }

    @AfterEach
    void clear() {
        transactionManager.rollback(transactionStatus);
    }

    @Test
    @DisplayName("기록한 예약들을 전부 가져온다.")
    void findAllTest() {
        // given
        List<String> times = List.of("10:00", "11:00", "12:00", "13:00", "14:00");
        for (String time : times) {
            ReservationTime save = reservationTimeRepository.save(reservationTime(time));
            reservationRepository.save(reservation(save));
        }
        // when
        List<ReservationResponse> responses = reservationFinder.findAll();
        // then
        assertThat(responses).isNotEmpty().hasSize(times.size());
    }

}