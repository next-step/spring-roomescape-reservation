package roomescape.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.theme.ThemeResponse;
import roomescape.dto.theme.create.ThemeCreateRequest;
import roomescape.dto.theme.create.ThemeCreateResponse;
import roomescape.exception.custom.DuplicatedThemeNameException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class ThemeServiceTest {


    @Autowired
    ThemeService themeService;

    @BeforeEach
    void init() {
        themeService.createTheme(new ThemeCreateRequest("테마1", "테마1의 설명은 비밀입니다.", "http://"));
    }

    @Test
    @DisplayName("테마의 리스트 테스트")
    void findThemes() {
        //given
        ThemeCreateRequest request = new ThemeCreateRequest("hello", "첫번째 테마", "테마이미지");
        themeService.createTheme(request);

        //when
        List<ThemeResponse> themes = themeService.findThemes();

        //then
        assertThat(themes).isNotEmpty();
        assertThat(themes).hasSize(1);
        assertThat(themes).allSatisfy((themeResponse -> {
            assertThat(themes.get(0).getId()).isEqualTo(1);
            assertThat(themes.get(0).getName()).isEqualTo("hello");
            assertThat(themes.get(0).getDescription()).isEqualTo("첫번째 테마");
            assertThat(themes.get(0).getThumbnail()).isEqualTo("테마이미지");
        }));
    }

    @Test
    @DisplayName("테마 생성")
    void create() {

        //given
        ThemeCreateRequest request = new ThemeCreateRequest("hello", "첫번째 테마", "테마이미지");

        //when
        ThemeCreateResponse response = themeService.createTheme(request);

        //then
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getName()).isEqualTo("hello");
        assertThat(response.getDescription()).isEqualTo(request.getDescription());
        assertThat(response.getThumbnail()).isEqualTo(request.getThumbnail());
    }

    @Test
    @DisplayName("테마 삭제")
    void delete() {
        //given
        ThemeCreateRequest request = new ThemeCreateRequest("hello", "첫번째 테마", "테마이미지");
        ThemeCreateResponse response = themeService.createTheme(request);

        //when
        themeService.deleteTheme(1L);

        //then
        assertThat(themeService.findThemes()).hasSize(0);
        assertThat(themeService.findThemes()).isEmpty();
    }

    @Test
    @DisplayName("중복 테마 등록시 예외 발생")
    void checkDuplicatedThemeName() {
        //init 메서드에 존재하는 테마 이름, 중복된 이름으로 DuplicatedThemeNameException 예외 발생.
        Assertions.assertThrows(DuplicatedThemeNameException.class, () -> {
            themeService.createTheme(new ThemeCreateRequest("테마1", "설명설명설명", "???"));
        });
    }

}