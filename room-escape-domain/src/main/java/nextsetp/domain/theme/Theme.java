package nextsetp.domain.theme;

public class Theme {
    private String name;
    private String desc;
    private Long price;

    public Theme(String name, String desc, Long price) {
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
