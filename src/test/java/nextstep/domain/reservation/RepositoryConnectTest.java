package nextstep.domain.reservation;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class RepositoryConnectTest {

  @Autowired
  JdbcTemplate jdbcTemplate;

  @DisplayName("H2 DB 접근을 확인한다.")
  @Test
  void jdbcTemplateTest() {
    // given
    // when
    // then
    assertThatCode(() -> jdbcTemplate.queryForObject("SELECT 1", Integer.class))
        .doesNotThrowAnyException();
  }
}
