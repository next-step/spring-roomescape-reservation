package nextstep.app.web.dto;

import nextstep.core.theme.Theme;

public class ThemeCreateWebRequest {
    private String name;
    private String desc;
    private Long price;

    private ThemeCreateWebRequest() {
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
