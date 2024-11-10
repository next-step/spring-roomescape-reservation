package roomescape.domain.theme.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.support.IntegrationTestSupport;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;

class ThemeRepositoryTest extends IntegrationTestSupport {

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
                .deleted(false)
                .build();

        // when
        final Theme actual = sut.save(theme);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getId().value()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo("name"),
                () -> assertThat(actual.getDescription()).isEqualTo("description"),
                () -> assertThat(actual.getThumbnail()).isEqualTo("https://thumbnail.com"),
                () -> assertThat(actual.isDeleted()).isFalse()
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
                .deleted(false)
                .build();
        final Theme originSaved = sut.save(origin);

        final Theme changed = Theme.builder()
                .id(originSaved.getId())
                .name("changed-name")
                .description("changed-description")
                .thumbnail("changed-https://thumbnail.com")
                .deleted(false)
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
        final Theme themeSaved = saveTheme("name1", "description1", "https://thumbnail.com1", false);
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
                .deleted(false)
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
        saveTheme("name1", "description1", "https://thumbnail.com1", true);
        saveTheme("name2", "description2", "https://thumbnail.com2", false);

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

    @Test
    void findNotDeletedThemes() {
        // given
        saveTheme("name1", "description1", "https://thumbnail.com1", false);
        saveTheme("name2", "description2", "https://thumbnail.com2", true);

        // when
        final List<Theme> actual = sut.findNotDeletedThemes();

        // then
        assertThat(actual).hasSize(1)
                .extracting("name", "description", "thumbnail")
                .containsOnly(
                        tuple("name1", "description1", "https://thumbnail.com1")
                );
    }

    @Test
    void findAllByIds() {
        // given
        final Theme theme1 = saveTheme("name1", "description1", "https://thumbnail.com1", true);
        final Theme theme2 = saveTheme("name2", "description2", "https://thumbnail.com2", true);

        final List<ThemeId> themeIds = List.of(theme1.getId(), theme2.getId());

        // when
        final List<Theme> actual = sut.findAllByIds(themeIds);

        // then
        assertThat(actual).hasSize(2)
                .extracting("name", "description", "thumbnail")
                .containsExactly(
                        tuple("name1", "description1", "https://thumbnail.com1"),
                        tuple("name2", "description2", "https://thumbnail.com2")
                );
    }

    private Theme saveTheme(String name, String description, String thumbnail, final boolean deleted) {
        final Theme theme = Theme.builder()
                .name(name)
                .description(description)
                .thumbnail(thumbnail)
                .deleted(deleted)
                .build();

        return sut.save(theme);
    }
}
