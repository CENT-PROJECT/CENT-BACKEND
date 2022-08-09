package goingmerry.cent.ararm;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class saveAlarmDto {

    private String fromEmail; // 누가

    // notnull
    private String toEmail; // 누구에게

    private String teamName; // 어떤 팀에서~

    // notnull
    private String value;

    @Builder
    public saveAlarmDto(String fromEmail, String toEmail, String teamName, String value) {
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.teamName = teamName;
        this.value = value;
    }

    public Alarm toEntity(saveAlarmDto saveDto) {
        return Alarm.builder()
                .fromEmail(saveDto.getFromEmail())
                .toEmail(saveDto.getToEmail())
                .teamName(saveDto.getTeamName())
                .value(saveDto.getValue())
                .build();
    }
}
