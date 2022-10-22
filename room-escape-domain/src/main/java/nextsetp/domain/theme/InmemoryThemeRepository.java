package nextsetp.domain.theme;

import java.util.List;
import java.util.Optional;

public class InmemoryThemeRepository implements ThemeRepository{
    @Override
    public Long save(Theme theme) {
        return null;
    }

    @Override
    public Optional<Theme> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Theme> findAll() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
