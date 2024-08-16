package roomescape.core.domain.theme;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.core.domain.common.ActiveStatus;

import static org.assertj.core.api.Assertions.assertThat;

class ThemeTest {

    @DisplayName("defaultOf로 테마 생성 시 AcitveStatus 는 ACTIVE")
    @Test
    void defaultOf() {
        final Theme actual = Theme.defaultOf("name", "description", "https://thumbnail.com");
        assertThat(actual.getActiveStatus()).isEqualTo(ActiveStatus.ACTIVE);
    }
}