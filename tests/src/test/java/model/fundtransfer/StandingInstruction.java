package model.fundtransfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.Arrays;

@Builder
public class StandingInstruction {

    @JsonProperty("StartDate")
    private String startDate;

    @JsonProperty("EndDate")
    private String endDate;

    @JsonProperty("Frequency")
    private String frequency;

    @JsonProperty("Every")
    private Long every;

    @JsonProperty("IsRecurring")
    private boolean isRecurring;

    @JsonProperty("DaysOfMonth")
    private Long[] daysOfMonth;

    @Override
    public String toString() {
        return "StandingInstruction{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", frequency='" + frequency + '\'' +
                ", every=" + every +
                ", isRecurring=" + isRecurring +
                ", daysOfMonth=" + Arrays.toString(daysOfMonth) +
                '}';
    }
}
