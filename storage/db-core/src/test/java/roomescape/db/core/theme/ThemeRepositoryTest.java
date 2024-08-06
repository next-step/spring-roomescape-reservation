package roomescape.db.core.theme;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.core.domain.theme.Theme;
import roomescape.core.domain.theme.ThemeRepository;
import roomescape.db.core.ApplicationContextTest;

import java.util.List;

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

    private void saveTheme(String name, String description, String thumbnail) {
        final Theme theme = Theme.builder()
                .name(name)
                .description(description)
                .thumbnail(thumbnail)
                .build();
        sut.save(theme);
    }
}