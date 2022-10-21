package nextstep.app.web.theme;

import nextstep.core.theme.in.ThemeCreateRequest;

class ThemeCreateWebRequest {
    private String name;
    private String desc;
    private Long price;

    private ThemeCreateWebRequest() {
    }

    public ThemeCreateRequest to() {
        return new ThemeCreateRequest(name, desc, price);
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
