package nextstep.schedule.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Theme {
    private final Long id;
    private final String name;
    private final String description;
    private final ThemePrice price;

    public Theme(String name, String description, BigDecimal price) {
        this(null, name, description, price);
    }

    public Theme(Long id, Theme theme) {
        this(id, theme.getName(), theme.getDescription(), theme.getPrice());
    }

    public Theme(Long id, String name, String description, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = new ThemePrice(price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price.getValue();
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
