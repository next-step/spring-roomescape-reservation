package nextstep.core.theme.in;

import nextstep.core.theme.Theme;

public class ThemeResponse {
    private final Long id;
    private final String name;
    private final String desc;
    private final Long price;

    private ThemeResponse(Long id, String name, String desc, Long price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public static ThemeResponse from(Theme theme) {
        return new ThemeResponse(
                theme.getId(),
                theme.getName(),
                theme.getDesc(),
                theme.getPrice()
        );
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

    public Long getPrice() {
        return price;
    }

}
