package roomescape.apply.reservationtime.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import roomescape.apply.reservationtime.domain.repository.ReservationTimeJDBCRepository;
import roomescape.apply.reservationtime.domain.repository.ReservationTimeRepository;
import roomescape.apply.reservationtime.ui.dto.ReservationTimeRequest;
import roomescape.apply.reservationtime.ui.dto.ReservationTimeResponse;
import roomescape.support.BaseTestService;

import static org.assertj.core.api.Assertions.assertThat;
import static roomescape.support.ReservationsFixture.reservationTimeRequest;

class ReservationTimeSaverTest extends BaseTestService {
    private ReservationTimeSaver reservationTimeSaver;
    private ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void setUp() {
        transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        reservationTimeRepository = new ReservationTimeJDBCRepository(template);
        reservationTimeSaver = new ReservationTimeSaver(reservationTimeRepository);
    }

    @AfterEach
    void clear() {
        transactionManager.rollback(transactionStatus);
    }

    @Test
    @DisplayName("신규 예약 시간 저장 테스트")
    void recordReservationBy() {
        // given
        assertThat(reservationTimeRepository.findAll()).isEmpty();
        ReservationTimeRequest request = reservationTimeRequest();
        // when
        ReservationTimeResponse response = reservationTimeSaver.saveReservationTimeBy(request);
        // then
        assertThat(response).isNotNull();
        assertThat(response.id()).isNotZero();
        assertThat(response.startAt()).isEqualTo(request.startAt());
    }

}