package nextstep.presentation.dto;

public class ThemeRequest {

    private String name;
    private String desc;
    private Integer price;

    private ThemeRequest() {
    }

    public ThemeRequest(String name, String desc, Integer price) {
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

    public Integer getPrice() {
        return price;
    }
}
