package roomescape.reservation;

public class ReservationResponseDto {
    private Long id;
    private String name;
    private String date;
    private String time;

    public ReservationResponseDto(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public static class Builder {
        private Long id;
        private String name;
        private String date;
        private String time;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public Builder time(String time) {
            this.time = time;
            return this;
        }

        public ReservationResponseDto build() {
            return new ReservationResponseDto(id, name, date, time);
        }
    }
}
