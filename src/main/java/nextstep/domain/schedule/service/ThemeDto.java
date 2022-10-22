package nextstep.domain.schedule.service;

import nextstep.domain.theme.model.Theme;

public class ThemeDto {
    private Long id;
    private String name;
    private String desc;
    private Long price;

    public ThemeDto(Theme theme) {
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
