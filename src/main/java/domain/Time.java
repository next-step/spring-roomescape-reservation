package domain;

public class Time {

    Long id;
    String startAt;

    public Time(String startAt) {
        this.startAt = startAt;
    }

    public String getStartAt() {
        return startAt;
    }
}
