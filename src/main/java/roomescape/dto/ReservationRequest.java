package roomescape.dto;

public class ReservationRequest {
    private String name;
    private String date;
    private String timeId;

    public ReservationRequest() {}

    public ReservationRequest(String name, String date, String timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeId() {
        return timeId;
    }

    public void setTimeId(String timeId) {
        this.timeId = timeId;
    }

    public Long getTimeIdAsLong() {
        try {
            return Long.parseLong(timeId);
        } catch (NumberFormatException e) {
            return null; // 변환 실패 시 null 반환 또는 적절한 예외 처리
        }
    }
}
