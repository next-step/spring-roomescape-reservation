package nextstep.app.web.theme;

import nextstep.core.theme.in.ThemeResponse;

class ThemeWebResponse {
    private final Long id;
    private final String name;
    private final String desc;
    private final Long price;

    private ThemeWebResponse(Long id, String name, String desc, Long price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public static ThemeWebResponse from(ThemeResponse theme) {
        return new ThemeWebResponse(
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
