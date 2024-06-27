package roomescape.infra.theme;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.theme.CreateTheme;
import roomescape.domain.theme.Theme;
import roomescape.domain.theme.ThemeSummary;

@Repository
public class ThemeRepository {

  private final ThemeJdbcRepository jdbcRepository;

  public ThemeRepository(ThemeJdbcRepository jdbcRepository) {
    this.jdbcRepository = jdbcRepository;
  }

  public boolean isUsedInReservation(Theme theme) {
    return jdbcRepository.isUsedInReservation(theme);
  }

  @Transactional
  public Theme create(CreateTheme theme) {
    long savedId = jdbcRepository.create(theme);
    return findOne(savedId);
  }

  public List<Theme> findList() {
    return jdbcRepository.findList().stream().map(ThemeEntity::toDomain).toList();
  }

  public List<ThemeSummary> findSummaries() {
    return jdbcRepository.findSummaries();
  }

  public Theme findOne(long id) {
    return jdbcRepository.findOne(id);
  }

  public ThemeSummary findSummaryOne(long id) {
    return jdbcRepository.findSummary(id);
  }

  public List<ThemeSummary> findSummaryByIds(List<Long> ids) {
    return jdbcRepository.findSummaryByIds(ids);
  }

  public void delete(long id) {
    jdbcRepository.delete(id);
  }
}
