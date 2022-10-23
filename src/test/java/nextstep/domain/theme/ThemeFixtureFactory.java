package nextstep.domain.theme;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import nextstep.domain.theme.dto.ThemeCommandDto;


public class ThemeFixtureFactory {

  private static final Long FIXTURE_ID = 1L;
  private static final String NAME = "넥스트스탭 코딩지옥";
  private static final BigDecimal PRICE = BigDecimal.valueOf(22000);
  private static final String DESCRIPTION = "100만년 동안 코딩을 해야 탈출할 수 있다. 리뷰어 5만명의 approve를 받아라!";

  private final Theme fixture;
  private final ThemeCommandDto.Create create;
  private final ThemeCommandDto.Delete delete;

  private ThemeFixtureFactory() {
    this.fixture = createTheme(FIXTURE_ID, NAME, PRICE, DESCRIPTION);
    this.create = toCreate(fixture);
    this.delete = ThemeCommandDto.Delete.builder()
        .id(FIXTURE_ID)
        .build();
  }

  private Theme createTheme(Long id, String name, BigDecimal price, String description) {
    return Theme.builder()
        .id(id)
        .name(name)
        .price(price)
        .description(description)
        .build();
  }

  private ThemeCommandDto.Create toCreate(Theme theme) {
    return ThemeCommandDto.Create.builder()
        .name(theme.getName().value())
        .price(theme.getPrice().value())
        .desc(theme.getDescription().value())
        .build();
  }

  public static ThemeFixtureFactory newInstance() {
    return new ThemeFixtureFactory();
  }

  /**
   * 테마의 생성 rule을 다 충족한 테마.
   * <pre>
   *   Theme(id = 1, name = 넥스트스탭 코딩지옥, price = 22000, description = 100만년 동안 코딩을 해야 탈출할 수 있다. 리뷰어 5만명의 approve를 받아라!)
   * </pre>
   */
  public Theme getFixture() {
    return fixture;
  }

  /**
   * size 만큼의 테마를 getFixture()에서 이름 뒤에 1을 붙이는 형태로 반환한다.
   * ex ) 넥스트스탭 코딩지옥1, 넥스트스탭 코딩지옥2 , 넥스트스탭 코딩지옥3, 넥스트스탭 코딩지옥4, ...
   *
   * @see ThemeFixtureFactory#getFixture() getFixture()
   *
   * <pre>
   *   var factory = ThemeFixtureFactory.newInstance();
   *   List<Theme> themeCustoms = factory.getFixtures(4);
   *   assertThat(themeCustoms).hasSize(4); // true
   * </pre>
   */
  public List<Theme> getFixtures(int size) {
    var fixture = getFixture();
    List<Theme> themes = new ArrayList<>();

    for (int number = 0; number < size; number++) {
      themes.add(
          Theme.builder()
              .id(fixture.getId() + number)
              .name("%s%d".formatted(fixture.getName(), number))
              .price(fixture.getPrice().value())
              .description(fixture.getDescription().value())
              .build()
      );
    }

    return themes;
  }

  /**
   * size 만큼의 테마를 getFixture()에서 이름 뒤에 1을 붙이는 형태로 반환한다.
   * ex ) 넥스트스탭 코딩지옥1, 넥스트스탭 코딩지옥2, 넥스트스탭 코딩지옥3, 넥스트스탭 코딩지옥4, ...
   *
   * @see ThemeFixtureFactory#getFixture() getFixture()
   * <pre>
   *   var factory = ThemeFixtureFactory.newInstance();
   *   List<Theme> themeCustoms = factory.getFixtures(4);
   *   assertThat(themeCustoms).hasSize(4); // true
   * </pre>
   */
  public List<ThemeCommandDto.Create> getFixturesCreateReq(int size) {
    List<Theme> fixtures = getFixtures(size);

    return fixtures.stream()
        .map(this::toCreate)
        .toList();
  }

  /**
   * 테마의 생성 rule을 다 충족한 테마 생성 객체. fixture와 값이 같음
   * <pre>
   *   ThemeCommandDto.Create(name = 넥스트스탭 코딩지옥, price = 22000, description = 100만년 동안 코딩을 해야 탈출할 수 있다. 리뷰어 5만명의 approve를 받아라!)
   * </pre>
   *
   * @see ThemeFixtureFactory#getFixture() getFixture()
   */
  public ThemeCommandDto.Create getFixtureCreateReq() {
    return create;
  }

  /**
   * 테마 ID 1을 삭제하는 삭제 요청 객체. fixture와 값이 같음
   * <pre>
   *   ThemeCommandDto.Delete(id = 1)
   * </pre>
   *
   * @see ThemeFixtureFactory#getFixture() getFixture()
   */
  public ThemeCommandDto.Delete getFixtureDeleteReq() {
    return delete;
  }

}
