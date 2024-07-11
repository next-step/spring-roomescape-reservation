package roomescape.domain;

public class Time {

    Long id;
    String startAt;

    public Time(String startAt) {
        this.startAt = startAt;
    }

    public Time() {
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }
}
