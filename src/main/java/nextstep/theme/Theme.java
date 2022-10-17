package nextstep.theme;

public class Theme {

    private Long id;
    private final String name;
    private final String desc;
    private final int price;

    public Theme(Long id, String name, String desc, int price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public static Theme of(String name, String desc, int price) {
        return new Theme(0L, name, desc, price);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
