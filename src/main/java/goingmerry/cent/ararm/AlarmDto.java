package goingmerry.cent.ararm;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Locale;


@Setter
@Getter
@NoArgsConstructor
public class AlarmDto {

    private Long id;

    private String fromEmail; // 누가

    // notnull
    private String toEmail; // 누구에게

    private String teamName; // 어떤 팀에서~

    // notnull
    private String value;

//    private String createDate;
    private LocalDateTime createDate;

    @Builder
    public AlarmDto(Alarm entity) {
        this.id = entity.getId();
        this.fromEmail = entity.getFromEmail();
        this.toEmail = entity.getToEmail();
        this.teamName = entity.getTeamName();
        this.value = entity.getValue();
        this.createDate = entity.getCreatedAt();
    }



}
