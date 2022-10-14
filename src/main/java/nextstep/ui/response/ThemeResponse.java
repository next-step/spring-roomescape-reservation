package nextstep.ui.response;

import static java.util.stream.Collectors.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import nextstep.domain.Theme;

public class ThemeResponse {

    private final Long id;
    private final String name;
    private final String desc;
    private final BigDecimal price;

    public ThemeResponse(Long id, String name, String desc, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public static ThemeResponse from(Theme theme) {
        return new ThemeResponse(
            theme.getId(),
            theme.getName(),
            theme.getDescription(),
            theme.getPrice()
        );
    }

    public static List<ThemeResponse> of(List<Theme> themes) {
        return themes.stream()
            .map(ThemeResponse::from)
            .collect(toList());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
