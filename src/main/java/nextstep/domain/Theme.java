package nextstep.domain;

public class Theme {
    private final Long id;
    private final String name;
    private final String desc;
    private final int price;

    public Theme(Long id, Theme theme) {
        this(id, theme.getName(), theme.getDesc(), theme.getPrice());
    }

    public Theme(String name, String desc, int price) {
        this(null, name, desc, price);
    }

    public Theme(Long id, String name, String desc, int price) {
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

    public int getPrice() {
        return price;
    }
}
