package roomescape.apply.reservationtime.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import roomescape.apply.reservation.application.ReservationFinder;
import roomescape.apply.reservation.domain.Reservation;
import roomescape.apply.reservation.domain.repository.ReservationJDBCRepository;
import roomescape.apply.reservation.domain.repository.ReservationRepository;
import roomescape.apply.reservationtime.application.exception.ReservationTimeReferencedException;
import roomescape.apply.reservationtime.domain.ReservationTime;
import roomescape.apply.reservationtime.domain.repository.ReservationTimeJDBCRepository;
import roomescape.apply.reservationtime.domain.repository.ReservationTimeRepository;
import roomescape.apply.theme.domain.Theme;
import roomescape.apply.theme.domain.repository.ThemeJDBCRepository;
import roomescape.apply.theme.domain.repository.ThemeRepository;
import roomescape.support.BaseTestService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static roomescape.support.ReservationsFixture.reservationTime;
import static roomescape.support.ReservationsFixture.theme;

class ReservationTimeDeleterTest extends BaseTestService {
    private ThemeRepository themeRepository;
    private ReservationRepository reservationRepository;
    private ReservationTimeDeleter reservationTimeDeleter;
    private ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void setUp() {
        transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        themeRepository = new ThemeJDBCRepository(template);
        reservationRepository = new ReservationJDBCRepository(template);
        reservationTimeRepository = new ReservationTimeJDBCRepository(template);
        var reservationFinder = new ReservationFinder(reservationRepository);
        reservationTimeDeleter = new ReservationTimeDeleter(reservationTimeRepository, reservationFinder);
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

    @Test
    @DisplayName("예약에서 사용중인 예약 시간은 삭제할 수 없어야 한다.")
    void canNotDeletedTest() {
        // given
        Theme theme = themeRepository.save(theme());
        ReservationTime time = reservationTimeRepository.save(reservationTime());
        // when
        reservationRepository.save(Reservation.of("사용중_테스트", "2999-12-31", time, theme));
        // then
        assertThatThrownBy(() -> reservationTimeDeleter.deleteReservationTimeBy(time.getId()))
                .isInstanceOf(ReservationTimeReferencedException.class)
                .hasMessage(ReservationTimeReferencedException.DEFAULT_MESSAGE);
    }
}