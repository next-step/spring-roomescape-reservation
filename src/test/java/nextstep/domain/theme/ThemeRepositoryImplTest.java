package nextstep.domain.theme;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class ThemeRepositoryImplTest {

  @Autowired
  ThemeRepositoryImpl themeRepository;
  @Autowired
  JdbcTemplate jdbcTemplate;

  @BeforeEach
  void setUp(){
    jdbcTemplate.update("delete from theme");
  }

  @DisplayName("테마 생성를 확인한다.")
  @Test
  void saveTest() {
    // given
    var fixtureFactory = ThemeFixtureFactory.newInstance();
    var fixtureCreateReq = fixtureFactory.getFixtureCreateReq();
    // when
    Theme save = themeRepository.save(fixtureCreateReq);
    // then
    assertThat(save.getName().value()).isEqualTo(fixtureCreateReq.name());
    assertThat(save.getDescription().value()).isEqualTo(fixtureCreateReq.desc());
    assertThat(save.getPrice().value()).isEqualTo(fixtureCreateReq.price());
  }

  @DisplayName("테마 전체 조회를 확인한다.")
  @Test
  void findAllTest() {
    // given
    var fixtureFactory = ThemeFixtureFactory.newInstance();
    var fixturesCreateReq = fixtureFactory.getFixturesCreateReq(5);
    fixturesCreateReq.forEach(
        create -> themeRepository.save(create)
    );

    // when
    var themes = themeRepository.findAll();

    // then
    assertThat(themes.size()).isEqualTo(fixturesCreateReq.size());
  }

  @DisplayName("테마 삭제를 확인한다.")
  @Test
  void deleteTest() {
    // given
    var fixtureFactory = ThemeFixtureFactory.newInstance();
    var fixtureCreateReq = fixtureFactory.getFixtureCreateReq();
    var fixturesDeleteReq = fixtureFactory.getFixtureDeleteReq();
    Theme save = themeRepository.save(fixtureCreateReq);

    // when & than - theme exist so removed
    boolean expectedTrue = themeRepository.delete(fixturesDeleteReq);
    assertThat(expectedTrue).isTrue();
    // when & than - theme not exist so not removed
    boolean expectedFalse = themeRepository.delete(fixturesDeleteReq);
    assertThat(expectedFalse).isFalse();
  }
}