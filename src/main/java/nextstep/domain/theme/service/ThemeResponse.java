package nextstep.domain.theme.service;

import nextstep.domain.theme.model.Theme;

public class ThemeResponse {
    private Long id;
    private String name;
    private String desc;
    private Long price;

    public ThemeResponse(Theme theme) {
        this.id = theme.getId();
        this.name = theme.getName();
        this.desc = theme.getDescription();
        this.price = theme.getPrice();
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
