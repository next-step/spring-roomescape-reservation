package nextstep.domain;

public class Theme {

    private Long id;
    private String name;
    private String desc;
    private Integer price;

    private Theme() {
    }

    public Theme(String name, String desc, Integer price) {
        this(null, name, desc, price);
    }

    public Theme(Long id, String name, String desc, Integer price) {
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
