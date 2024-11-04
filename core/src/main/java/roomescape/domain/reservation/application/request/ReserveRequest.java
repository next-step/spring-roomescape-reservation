package roomescape.domain.reservation.application.request;

import lombok.Builder;
import lombok.Getter;
import roomescape.domain.common.exception.Assert;

import java.time.LocalDate;

@Getter
public class ReserveRequest {

    private String name;
    private LocalDate date;
    private Long timeId;
    private Long themeId;

    public ReserveRequest() {
    }

    @Builder
    private ReserveRequest(final String name, final LocalDate date, final Long timeId, final Long themeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
        this.themeId = themeId;
    }

    public void validateAllFieldsExist() {
        Assert.notNullField(name, "name");
        Assert.notNullField(date, "date");
        Assert.notNullField(timeId, "timeId");
        Assert.notNullField(themeId, "themeId");
    }
}
