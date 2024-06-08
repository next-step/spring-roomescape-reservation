package roomescape.apply.theme.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import roomescape.apply.theme.domain.Theme;
import roomescape.apply.theme.domain.repository.ThemeJDBCRepository;
import roomescape.apply.theme.domain.repository.ThemeRepository;
import roomescape.support.BaseTestService;

import static org.assertj.core.api.Assertions.assertThat;
import static roomescape.support.ReservationsFixture.theme;

class ThemeDeleterTest extends BaseTestService {
    private ThemeDeleter themeDeleter;
    private ThemeRepository themeRepository;

    @BeforeEach
    void setUp() {
        transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        themeRepository = new ThemeJDBCRepository(template);
        themeDeleter = new ThemeDeleter(themeRepository);
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

}