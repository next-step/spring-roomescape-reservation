package nextsetp.domain.theme;

import java.util.List;

public interface ThemeRepository {
    Long save(Theme theme);
    List<Theme> findAll();
    void delete(Long id);
}
