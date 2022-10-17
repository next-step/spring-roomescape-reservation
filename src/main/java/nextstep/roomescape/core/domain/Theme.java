package nextstep.roomescape.core.domain;

public class Theme {

    private final Integer id;
    private final String name;
    private final String desc;
    private final int price;

    public Theme(Integer id, String name, String desc, int price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Integer id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String desc() {
        return desc;
    }

    public int price() {
        return price;
    }
}
