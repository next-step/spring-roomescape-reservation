package nextstep.domain.theme.domain.model;

public record Theme(Long id,
                    String name,
                    String description,
                    Long price) {

    public Theme withId(Long id) {
        return new Theme(id, name, description, price);
    }
}
