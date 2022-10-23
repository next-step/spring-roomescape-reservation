package nextstep.domain.theme.model;

import nextstep.domain.Identity;

public class Theme {
    private Long id;
    private String name;
    private String description;
    private Long price;

    public Theme(Long id, String name, String description, Long price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Theme withId() {
        this.id = Identity.getId(Theme.class);
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getPrice() {
        return price;
    }
}
