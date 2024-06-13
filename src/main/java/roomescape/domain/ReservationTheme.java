package roomescape.domain;

public class ReservationTheme {

    private Long id;
    private String name;
    private String description;
    private String thumbnail;

    public ReservationTheme() {
    }

    public ReservationTheme(Long id) {
        this.id = id;
    }

    public ReservationTheme(Long id, String name, String description, String thumbnail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public ReservationTheme(String name, String description, String thumbnail) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public ReservationTheme toEntity(ReservationTheme reservationTheme, Long id) {
        return new ReservationTheme(id
                , reservationTheme.getName()
                , reservationTheme.getDescription()
                , reservationTheme.getThumbnail());
    }
}
