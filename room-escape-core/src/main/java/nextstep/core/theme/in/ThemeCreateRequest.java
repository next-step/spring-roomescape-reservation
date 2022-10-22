package nextstep.core.theme.in;

import nextstep.core.theme.Theme;

public class ThemeCreateRequest {
    private final String name;
    private final String desc;
    private final Long price;

    public ThemeCreateRequest(String name, String desc, Long price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Theme to() {
        return new Theme(name, desc, price);
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
