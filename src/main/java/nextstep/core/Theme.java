package nextstep.core;

public class Theme {
    private final Long id;
    private final String name;
    private final String desc;
    private final Long price;

    public Theme(String name, String desc, Long price) {
        this(null, name, desc, price);
    }

    public Theme(Long id, String name, String desc, Long price) {
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

    public Long getPrice() {
        return price;
    }
}
