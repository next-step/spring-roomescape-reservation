package roomescape.apply.time.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import roomescape.apply.time.domain.repository.ReservationTimeJDBCRepository;
import roomescape.apply.time.domain.repository.ReservationTimeRepository;
import roomescape.apply.time.ui.dto.ReservationTimeResponse;
import roomescape.support.BaseTestService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static roomescape.support.ReservationsFixture.reservationTime;

class ReservationTimeFinderTest extends BaseTestService {

    private ReservationTimeFinder reservationTimeFinder;
    private ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void setUp() {
        transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        reservationTimeRepository = new ReservationTimeJDBCRepository(template);
        reservationTimeFinder = new ReservationTimeFinder(reservationTimeRepository);
    }

    @AfterEach
    void clear() {
        transactionManager.rollback(transactionStatus);
    }

    @Test
    @DisplayName("저장한 예약 시간을 전부 가져온다.")
    void findAllTest() {
        // given
        List<String> times = List.of("10:00", "11:00", "12:00", "13:00", "14:00");
        for (String time : times) {
            reservationTimeRepository.save(reservationTime(time));
        }
        // when
        List<ReservationTimeResponse> responses = reservationTimeFinder.findAll();
        // then
        assertThat(responses).isNotEmpty().hasSize(times.size());
    }

}