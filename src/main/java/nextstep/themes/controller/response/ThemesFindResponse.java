package nextstep.themes.controller.response;

import nextstep.themes.Themes;

public class ThemesFindResponse {

    private final Long id;
    private final String name;
    private final String desc;
    private final Long price;

    public ThemesFindResponse(Long id, String name, String desc, Long price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public static ThemesFindResponse from(Themes themes) {
        return new ThemesFindResponse(themes.getId(), themes.getName(), themes.getDesc(), themes.getPrice());
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
