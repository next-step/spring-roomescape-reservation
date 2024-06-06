package roomescape.apply.reservation.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import roomescape.apply.reservation.domain.Reservation;
import roomescape.apply.reservation.domain.repository.ReservationJDBCRepository;
import roomescape.apply.reservation.domain.repository.ReservationRepository;
import roomescape.apply.reservationtime.domain.ReservationTime;
import roomescape.apply.reservationtime.domain.repository.ReservationTimeJDBCRepository;
import roomescape.apply.reservationtime.domain.repository.ReservationTimeRepository;
import roomescape.support.BaseTestService;

import static org.assertj.core.api.Assertions.assertThat;
import static roomescape.support.ReservationsFixture.reservation;
import static roomescape.support.ReservationsFixture.reservationTime;

class ReservationCancelerTest extends BaseTestService {

    private ReservationCanceler reservationCanceler;
    private ReservationRepository reservationRepository;
    private ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void setUp() {
        transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        reservationRepository = new ReservationJDBCRepository(template);
        reservationTimeRepository = new ReservationTimeJDBCRepository(template);
        reservationCanceler = new ReservationCanceler(reservationRepository);
    }

    @AfterEach
    void clear() {
        transactionManager.rollback(transactionStatus);
    }

    @Test
    @DisplayName("예약 취소 테스트")
    void cancelTest() {
        // given
        ReservationTime save = reservationTimeRepository.save(reservationTime());
        Reservation saved = reservationRepository.save(reservation(save));
        assertThat(reservationRepository.findAll().size()).isNotZero();
        // when
        reservationCanceler.cancelReservation(saved.getId());
        // then
        assertThat(reservationRepository.findAll()).isEmpty();
    }

}