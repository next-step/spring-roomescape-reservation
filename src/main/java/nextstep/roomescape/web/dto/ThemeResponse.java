package nextstep.roomescape.web.dto;

import nextstep.roomescape.core.domain.Theme;

public class ThemeResponse {

    private final Integer id;
    private final String name;
    private final String desc;
    private final int price;

    public ThemeResponse(Integer id, String name, String desc, int price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public static ThemeResponse of(Theme theme) {
        return new ThemeResponse(theme.id(), theme.name(), theme.desc(), theme.price());
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getPrice() {
        return price;
    }
}
