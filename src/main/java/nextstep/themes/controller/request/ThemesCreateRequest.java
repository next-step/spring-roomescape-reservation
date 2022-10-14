package nextstep.themes.controller.request;

public class ThemesCreateRequest {

    private final String name;
    private final String desc;
    private final Long price;

    public ThemesCreateRequest(String name, String desc, Long price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
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
