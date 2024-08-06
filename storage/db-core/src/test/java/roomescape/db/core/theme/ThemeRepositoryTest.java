package roomescape.db.core.theme;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.core.domain.theme.Theme;
import roomescape.core.domain.theme.ThemeId;
import roomescape.core.domain.theme.ThemeRepository;
import roomescape.db.core.ApplicationContextTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;

class ThemeRepositoryTest extends ApplicationContextTest {

    @Autowired
    ThemeRepository sut;

    @DisplayName("테마를 저장할 수 있다.")
    @Test
    void save() {
        // given
        final Theme theme = Theme.builder()
                .name("name")
                .description("description")
                .thumbnail("https://thumbnail.com")
                .build();

        // when
        final Theme actual = sut.save(theme);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getId().value()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo("name"),
                () -> assertThat(actual.getDescription()).isEqualTo("description"),
                () -> assertThat(actual.getThumbnail()).isEqualTo("https://thumbnail.com")
        );
    }

    @DisplayName("테마를 저장 시 ID가 이미 존재하면 모든 데이터를 업데이트한다.")
    @Test
    void save_already_id_exists() {
        // given
        final Theme origin = Theme.builder()
                .name("name")
                .description("description")
                .thumbnail("https://thumbnail.com")
                .build();
        final Theme originSaved = sut.save(origin);

        final Theme changed = Theme.builder()
                .id(originSaved.getId())
                .name("changed-name")
                .description("changed-description")
                .thumbnail("changed-https://thumbnail.com")
                .build();

        // when
        final Theme actual = sut.save(changed);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getId().value()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo("changed-name"),
                () -> assertThat(actual.getDescription()).isEqualTo("changed-description"),
                () -> assertThat(actual.getThumbnail()).isEqualTo("changed-https://thumbnail.com")
        );
    }

    @Test
    void findById_exists() {
        // given
        final Theme themeSaved = saveTheme("name1", "description1", "https://thumbnail.com1");
        final ThemeId themeId = themeSaved.getId();

        // when
        final Optional<Theme> actualOpt = sut.findById(themeId);

        // then
        assertThat(actualOpt).isPresent();
        final Theme actual = actualOpt.get();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo("name1"),
                () -> assertThat(actual.getDescription()).isEqualTo("description1"),
                () -> assertThat(actual.getThumbnail()).isEqualTo("https://thumbnail.com1")
        );
    }

    @Test
    void findById_not_exists() {
        // given
        final Theme notSaved = Theme.builder()
                .id(new ThemeId(-300L))
                .name("name")
                .description("description")
                .thumbnail("https://thumbnail.com")
                .build();
        final ThemeId themeId = notSaved.getId();

        // when
        final Optional<Theme> actualOpt = sut.findById(themeId);

        // then
        assertThat(actualOpt).isEmpty();
    }

    @Test
    void findAll() {
        // given
        saveTheme("name1", "description1", "https://thumbnail.com1");
        saveTheme("name2", "description2", "https://thumbnail.com2");

        // when
        final List<Theme> actual = sut.findAll();

        // then
        assertThat(actual).hasSize(2)
                .extracting("name", "description", "thumbnail")
                .containsOnly(
                        tuple("name1", "description1", "https://thumbnail.com1"),
                        tuple("name2", "description2", "https://thumbnail.com2")
                );
    }

    private Theme saveTheme(String name, String description, String thumbnail) {
        final Theme theme = Theme.builder()
                .name(name)
                .description(description)
                .thumbnail(thumbnail)
                .build();
        return sut.save(theme);
    }
}