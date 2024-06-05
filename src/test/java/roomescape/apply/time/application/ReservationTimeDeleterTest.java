package roomescape.apply.time.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import roomescape.apply.time.domain.ReservationTime;
import roomescape.apply.time.domain.repository.ReservationTimeJDBCRepository;
import roomescape.apply.time.domain.repository.ReservationTimeRepository;
import roomescape.support.BaseTestService;

import static org.assertj.core.api.Assertions.assertThat;
import static roomescape.support.ReservationsFixture.reservationTime;

class ReservationTimeDeleterTest extends BaseTestService {
    private ReservationTimeDeleter reservationTimeDeleter;
    private ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void setUp() {
        transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        reservationTimeRepository = new ReservationTimeJDBCRepository(template);
        reservationTimeDeleter = new ReservationTimeDeleter(reservationTimeRepository);
    }

    @AfterEach
    void clear() {
        transactionManager.rollback(transactionStatus);
    }

    @Test
    @DisplayName("예약 시간 삭제 테스트")
    void cancelTest() {
        // given
        ReservationTime save = reservationTimeRepository.save(reservationTime());
        assertThat(reservationTimeRepository.findAll().size()).isNotZero();
        // when
        reservationTimeDeleter.deleteReservationTimeBy(save.getId());
        // then
        assertThat(reservationTimeRepository.findAll()).isEmpty();
    }

}