package roomescape.domain.theme;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.infra.theme.ThemeRepository;

@Service
public class ThemeService {
  private final ThemeRepository repository;

  public ThemeService(ThemeRepository repository) {
    this.repository = repository;
  }

  public List<Theme> findAll() {
    return repository.findAll();
  }

  @Transactional
  public Theme create(CreateTheme theme) {
    long savedId = repository.create(theme);
    return repository.findOne(savedId);
  }

  @Transactional
  public void delete(long id) {
    Theme one = repository.findOne(id);
    if (one == null) throw new ThemeNotFound();
    if(repository.isUsedInReservation(one)) throw new ThemeIsUsed();
    repository.delete(id);
  }
}
