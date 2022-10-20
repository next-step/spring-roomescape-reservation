package nextstep.app.web.dto;

import nextstep.core.Theme;

public class ThemeCreateRequest {
    private String name;
    private String desc;
    private Long price;

    private ThemeCreateRequest() {
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
