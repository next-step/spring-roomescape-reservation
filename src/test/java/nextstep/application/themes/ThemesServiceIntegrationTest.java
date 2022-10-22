package nextstep.application.themes;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import nextstep.application.themes.dto.Themes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@Sql("/truncate.sql")
@SpringBootTest
@DisplayName("테마 서비스 통합 테스트")
class ThemesServiceIntegrationTest {

  @Autowired
  private ThemesService sut;

  @Test
  void 테마_생성한다() {
    //given
    var themes = Themes.builder()
        .name("테마이름")
        .desc("테마설명")
        .price(BigDecimal.valueOf(22000))
        .build();
    //when
    var actual = sut.create(themes);
    //then
    assertThat(actual).isNotNull();
  }

  @Test
  void 전체_테마_조회환다() {
    //given
    var themes = Themes.builder()
        .name("테마이름")
        .desc("테마설명")
        .price(BigDecimal.valueOf(22000))
        .build();
    sut.create(themes);
    //when
    var actual = sut.findAllThemes();
    //then
    assertThat(actual).hasSize(1);
  }

  @Test
  void 테마_삭제_성공한다() {
    //given
    var themes = Themes.builder()
        .name("테마이름")
        .desc("테마설명")
        .price(BigDecimal.valueOf(22000))
        .build();
    var themesId = sut.create(themes);
    //when
    sut.delete(themesId);
    var actual = sut.findAllThemes();
    //then
    assertThat(actual).hasSize(0);
  }
}
