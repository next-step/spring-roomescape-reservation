package nextstep.ui.request;

import java.math.BigDecimal;

public class ThemeCreateRequest {

    private String name;
    private String desc;
    private BigDecimal price;

    protected ThemeCreateRequest() {
    }

    public ThemeCreateRequest(String name, String desc, BigDecimal price) {
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

    public BigDecimal getPrice() {
        return price;
    }
}
