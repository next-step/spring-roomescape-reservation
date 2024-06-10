package roomescape.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.dto.theme.ThemeResponse;
import roomescape.dto.theme.create.ThemeCreateRequest;
import roomescape.dto.theme.create.ThemeCreateResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ThemeServiceTest {


    @Autowired
    ThemeService themeService;

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


}