package nextstep.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Theme {
    private final Long id;
    private final String name;
    private final String description;
    private final ThemePrice price;

    public Theme(String name, String description, BigDecimal price) {
        this(null, name, description, new ThemePrice(price));
    }

    public Theme(Long id, String name, String description, ThemePrice price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Theme theme = (Theme) o;
        return Objects.equals(id, theme.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
