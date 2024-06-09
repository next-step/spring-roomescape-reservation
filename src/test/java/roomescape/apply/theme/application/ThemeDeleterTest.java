package roomescape.apply.theme.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import roomescape.apply.reservation.application.ReservationFinder;
import roomescape.apply.reservation.domain.Reservation;
import roomescape.apply.reservation.domain.repository.ReservationJDBCRepository;
import roomescape.apply.reservation.domain.repository.ReservationRepository;
import roomescape.apply.reservationtime.domain.ReservationTime;
import roomescape.apply.reservationtime.domain.repository.ReservationTimeJDBCRepository;
import roomescape.apply.reservationtime.domain.repository.ReservationTimeRepository;
import roomescape.apply.theme.application.exception.ThemeReferencedException;
import roomescape.apply.theme.domain.Theme;
import roomescape.apply.theme.domain.repository.ThemeJDBCRepository;
import roomescape.apply.theme.domain.repository.ThemeRepository;
import roomescape.support.BaseTestService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static roomescape.support.ReservationsFixture.reservationTime;
import static roomescape.support.ReservationsFixture.theme;

class ThemeDeleterTest extends BaseTestService {
    private ThemeDeleter themeDeleter;
    private ThemeRepository themeRepository;
    private ReservationRepository reservationRepository;
    private ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void setUp() {
        transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        themeRepository = new ThemeJDBCRepository(template);
        reservationTimeRepository = new ReservationTimeJDBCRepository(template);
        reservationRepository = new ReservationJDBCRepository(template);
        var reservationFinder = new ReservationFinder(reservationRepository);
        themeDeleter = new ThemeDeleter(themeRepository, reservationFinder);
    }

    @AfterEach
    void clear() {
        transactionManager.rollback(transactionStatus);
    }

    @Test
    @DisplayName("테마 삭제 테스트")
    void cancelTest() {
        // given
        Theme save = themeRepository.save(theme());
        assertThat(themeRepository.findAll().size()).isNotZero();
        // when
        themeDeleter.deleteThemeBy(save.getId());
        // then
        assertThat(themeRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("예약에서 사용중인 테마는 삭제할 수 없어야 한다.")
    void canNotDeletedTest() {
        // given
        Theme theme = themeRepository.save(theme());
        ReservationTime time = reservationTimeRepository.save(reservationTime());
        // when
        reservationRepository.save(Reservation.of("사용중_테스트", "2999-12-31", time, theme));
        // then
        assertThatThrownBy(() -> themeDeleter.deleteThemeBy(theme.getId()))
                .isInstanceOf(ThemeReferencedException.class)
                .hasMessage(ThemeReferencedException.DEFAULT_MESSAGE);
    }

}