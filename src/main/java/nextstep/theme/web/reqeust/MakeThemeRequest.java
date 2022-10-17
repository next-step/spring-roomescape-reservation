package nextstep.theme.web.reqeust;

import nextstep.theme.domain.Theme;

public class MakeThemeRequest {

    private final String name;
    private final String desc;
    private final Long price;

    public MakeThemeRequest(String name, String desc, Long price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Theme toTheme() {
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
