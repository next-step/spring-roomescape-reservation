package roomescape.controller;

public class ReservationRequestDto {
    private String name;
    private String date;
    private String time;


    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public ReservationRequestDto(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }


    public static class Builder {
        private String name;
        private String date;
        private String time;

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

        public ReservationRequestDto build() {
            return new ReservationRequestDto(name, date, time);
        }
    }
}
