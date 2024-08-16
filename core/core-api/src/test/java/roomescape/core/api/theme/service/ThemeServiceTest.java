package roomescape.core.api.theme.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.core.api.support.IntegrationTestSupport;
import roomescape.core.api.theme.service.request.ThemeAppendRequest;
import roomescape.core.domain.common.ActiveStatus;
import roomescape.core.domain.theme.Theme;
import roomescape.core.domain.theme.ThemeId;
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
                () -> assertThat(actual.getThumbnail()).isEqualTo("https://thumbnail.com"),
                () -> assertThat(actual.getActiveStatus()).isEqualTo(ActiveStatus.ACTIVE)
        );
    }

    @DisplayName("id로 테마를 삭제할 수 있다")
    @Test
    void deleteTheme() {
        // given
        final Theme themeToDelete = saveTheme("name1", "description1", "https://thumbnail.com1");
        final ThemeId themeId = themeToDelete.getId();

        // when
        sut.deleteTheme(themeId);

        // then
        final Theme actual = themeRepository.findById(themeId).get();
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo("name1"),
                () -> assertThat(actual.getDescription()).isEqualTo("description1"),
                () -> assertThat(actual.getThumbnail()).isEqualTo("https://thumbnail.com1"),
                () -> assertThat(actual.getActiveStatus()).isEqualTo(ActiveStatus.DELETED)
        );
    }

    private Theme saveTheme(String name, String description, String thumbnail) {
        final Theme theme = Theme.builder()
                .name(name)
                .description(description)
                .thumbnail(thumbnail)
                .activeStatus(ActiveStatus.ACTIVE)
                .build();
        return themeRepository.save(theme);
    }
}