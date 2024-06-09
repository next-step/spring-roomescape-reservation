package roomescape.apply.theme.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import roomescape.apply.theme.domain.repository.ThemeJDBCRepository;
import roomescape.apply.theme.domain.repository.ThemeRepository;
import roomescape.apply.theme.ui.dto.ThemeResponse;
import roomescape.support.BaseTestService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static roomescape.support.ReservationsFixture.theme;

class ThemeFinderTest extends BaseTestService {

    private ThemeFinder themeFinder;
    private ThemeRepository themeRepository;

    @BeforeEach
    void setUp() {
        transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        themeRepository = new ThemeJDBCRepository(template);
        themeFinder = new ThemeFinder(themeRepository);
    }

    @AfterEach
    void clear() {
        transactionManager.rollback(transactionStatus);
    }

    @Test
    @DisplayName("저장된 테마들을 전부 가져온다.")
    void findAllTest() {
        // given
        List<String> themeNames = List.of("호그와트", "기숙사", "감옥", "폐가", "병원");
        for (String name : themeNames) {
            themeRepository.save(theme(name, name + "을 탈출하는 내용입니다."));
        }
        // when
        List<ThemeResponse> responses = themeFinder.findAll();
        // then
        assertThat(responses).isNotEmpty().hasSize(themeNames.size());
    }

}