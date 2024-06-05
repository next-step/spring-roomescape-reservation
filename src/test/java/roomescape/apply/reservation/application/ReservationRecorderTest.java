package roomescape.apply.reservation.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import roomescape.apply.reservation.domain.repository.ReservationJDBCRepository;
import roomescape.apply.reservation.ui.dto.ReservationRequest;
import roomescape.apply.reservation.ui.dto.ReservationResponse;
import roomescape.apply.time.application.ReservationTimeFinder;
import roomescape.apply.time.domain.ReservationTime;
import roomescape.apply.time.domain.repository.ReservationTimeJDBCRepository;
import roomescape.apply.time.domain.repository.ReservationTimeRepository;
import roomescape.support.BaseTestService;

import static org.assertj.core.api.Assertions.assertThat;
import static roomescape.support.ReservationsFixture.reservationRequest;
import static roomescape.support.ReservationsFixture.reservationTime;

class ReservationRecorderTest extends BaseTestService {

    private ReservationRecorder reservationRecorder;
    private ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void setUp() {
        transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        reservationTimeRepository = new ReservationTimeJDBCRepository(template);
        reservationRecorder = new ReservationRecorder(new ReservationJDBCRepository(template), new ReservationTimeFinder(reservationTimeRepository));
    }

    @AfterEach
    void clear() {
        transactionManager.rollback(transactionStatus);
    }

    @Test
    @DisplayName("신규 예약 저장 테스트")
    void recordReservationBy() {
        // given
        ReservationTime time = reservationTimeRepository.save(reservationTime());
        ReservationRequest request = reservationRequest(time.getId());
        // when
        ReservationResponse response = reservationRecorder.recordReservationBy(request);
        // then
        assertThat(response).isNotNull();
        assertThat(response.id()).isNotZero();
        assertThat(response.name()).isEqualTo(request.name());
        assertThat(response.date()).isEqualTo(request.date());
        assertThat(response.time().id()).isEqualTo(time.getId());
        assertThat(response.time().startAt()).isEqualTo(time.getStartAt());
    }

}