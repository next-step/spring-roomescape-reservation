package nextstep.domain.theme.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ThemeMemoryRepository implements ThemeRepository {
    private static final List<Theme> themes = new ArrayList<>();

    @Override
    public Long create(Theme theme) {
        Theme themeWithId = theme.withId();

        themes.add(themeWithId);

        return themeWithId.getId();
    }

    @Override
    public List<Theme> findAll() {
        return themes;
    }

    @Override
    public Optional<Theme> findById(Long id) {
        return themes.stream()
            .filter(it -> Objects.equals(it.getId(), id))
            .findFirst();
    }

    @Override
    public void remove(Long id) {
        findById(id).ifPresent(themes::remove);
    }
}
