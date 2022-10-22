package nextstep.ui;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;
import nextstep.ApiDocument;
import nextstep.application.themes.ThemeService;
import nextstep.application.themes.dto.Theme;
import nextstep.application.themes.dto.ThemeRes;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(ThemeController.class)
class ThemeControllerTest extends ApiDocument {

  @MockBean
  private ThemeService service;

  @Test
  void 테마_생성한다() throws Exception {
    //given
    var theme = Theme.builder()
        .name("테마이름")
        .desc("테마설명")
        .price(BigDecimal.valueOf(22000))
        .build();
    given(service.create(theme)).willReturn(1L);
    //when
    var actual = mockMvc.perform(post("/themes")
        .contentType(MediaType.APPLICATION_JSON)
        .content(toJson(theme))
    );
    //then
    actual.andExpect(status().isCreated())
        .andDo(print())
        .andDo(toDocument("create-theme"));
  }

  @Test
  void 테마_조회한다() throws Exception {
    //given
    var theme = ThemeRes.builder()
        .id(1L)
        .name("테마이름")
        .desc("테마설명")
        .price(BigDecimal.valueOf(22000))
        .build();
    given(service.getThemes()).willReturn(List.of(theme));
    //when
    var actual = mockMvc.perform(get("/themes")
        .contentType(MediaType.APPLICATION_JSON)
    );
    //then
    actual.andExpect(status().isOk())
        .andDo(print())
        .andDo(toDocument("get-themes"));
  }

  @Test
  void 테마_삭제한다() throws Exception {
    //given
    var id = 1L;
    willDoNothing().given(service).delete(id);
    //when
    var actual = mockMvc.perform(delete("/themes/" + id));
    //then
    actual.andExpect(status().isNoContent())
        .andDo(print())
        .andDo(toDocument("remove-theme"));
  }
}
