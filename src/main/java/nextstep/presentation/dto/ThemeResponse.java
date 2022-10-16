package nextstep.presentation.dto;

public class ThemeResponse {

    private Long id;
    private String name;
    private String desc;
    private Integer price;

    private ThemeResponse() {
    }

    public ThemeResponse(Long id, String name, String desc, Integer price) {
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

    public Integer getPrice() {
        return price;
    }
}
