package nextstep.dto;

import nextstep.domain.Theme;

public class ThemeFindResponse {
    private final Long id;
    private final String name;
    private final String desc;
    private final int price;

    private ThemeFindResponse(Long id, String name, String desc, int price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
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

    public int getPrice() {
        return price;
    }

    public static ThemeFindResponse from(Theme theme) {
        return new ThemeFindResponse(
                theme.getId(),
                theme.getName(),
                theme.getDesc(),
                theme.getPrice()
        );
    }
}
