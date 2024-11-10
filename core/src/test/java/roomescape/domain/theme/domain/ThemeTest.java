package roomescape.domain.theme.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ThemeTest {

    @DisplayName("테마 생성 시 삭제 여부는 false")
    @Test
    void defaultOf() {
        final Theme actual = Theme.defaultOf("name", "description", "https://thumbnail.com");
        assertThat(actual.isDeleted()).isFalse();
    }
}