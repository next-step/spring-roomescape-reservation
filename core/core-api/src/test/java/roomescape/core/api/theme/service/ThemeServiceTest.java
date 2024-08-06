package roomescape.core.api.theme.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.core.api.support.IntegrationTestSupport;
import roomescape.core.api.theme.service.request.ThemeAppendRequest;
import roomescape.core.domain.theme.Theme;
import roomescape.core.domain.theme.ThemeRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ThemeServiceTest extends IntegrationTestSupport {

    @Autowired
    ThemeService sut;

    @Autowired
    ThemeRepository themeRepository;

    @DisplayName("테마를 추가할 수 있다")
    @Test
    void appendTheme() {
        // given
        final ThemeAppendRequest request = ThemeAppendRequest.builder()
                .name("name")
                .description("description")
                .thumbnail("https://thumbnail.com")
                .build();

        // when
        final Theme actual = sut.appendTheme(request);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo("name"),
                () -> assertThat(actual.getDescription()).isEqualTo("description"),
                () -> assertThat(actual.getThumbnail()).isEqualTo("https://thumbnail.com")
        );
    }
}