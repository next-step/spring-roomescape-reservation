package nextstep.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import nextstep.application.ThemeService;
import nextstep.exception.ThemeException;
import nextstep.presentation.dto.ThemeRequest;
import nextstep.presentation.dto.ThemeResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ThemeServiceTest {

    @Autowired
    private ThemeService themeService;

    @BeforeEach
    void setUp() {
        ThemeRequest request = new ThemeRequest("열쇠공이", "열쇠공의 이중생활", 25000);
        themeService.create(request);
    }

    @AfterEach
    void tearDown() {
        themeService.deleteAll();
    }

    @DisplayName("테마를 생성한다.")
    @Test
    void create_success() {
        // given
        ThemeRequest request = new ThemeRequest("해리포터", "헤르미온느와 마법 수업", 30000);

        // when
        Long themeId = themeService.create(request);

        // then
        assertThat(themeId).isNotNull();
    }

    @DisplayName("테마를 생성할 때, `이름`이 똑같은 테마가 있으면 테마를 생성할 수 없다.")
    @Test
    void create_fail() {
        // given
        ThemeRequest request = new ThemeRequest("열쇠공이", "열쇠공의 이중생활 시즌 2", 28000);

        // when
        // then
        assertThatThrownBy(() -> themeService.create(request))
            .isInstanceOf(ThemeException.class)
            .hasMessage("열쇠공이는(은) 이미 존재하는 테마입니다.");
    }

    @DisplayName("테마 목록을 조회한다.")
    @Test
    void checkAll() {
        // given
        ThemeRequest request = new ThemeRequest("해리포터", "헤리미온느와 마법 수업", 30000);
        themeService.create(request);

        List<ThemeResponse> expected = List.of(
            new ThemeResponse(null, "열쇠공이", "열쇠공의 이중생활", 25000),
            new ThemeResponse(null, "해리포터", "헤리미온느와 마법 수업", 30000)
        );

        // when
        List<ThemeResponse> responses = themeService.checkAll();

        // then
        assertThat(responses)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(expected);
    }

    @DisplayName("테마를 삭제한다.")
    @Test
    void delete() {
        // given
        Long themeId = themeService.checkAll().stream()
            .filter(it -> "열쇠공이".equals(it.getName()))
            .findFirst()
            .map(ThemeResponse::getId)
            .orElseThrow();

        // when
        // then
        assertThatCode(() -> themeService.delete(themeId)).doesNotThrowAnyException();
    }
}
