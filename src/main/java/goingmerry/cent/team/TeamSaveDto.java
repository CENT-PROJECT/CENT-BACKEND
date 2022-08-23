package goingmerry.cent.team;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamSaveDto {

    private String teamName;

    private String logo;

    private String intro;

    private String area;

    private Long leaderId;

    private String formationName;

    @Builder
    public TeamSaveDto(String teamName, String logo, String intro, String area, Long leaderId, String formationName) {
        this.teamName = teamName;
        this.logo = logo;
        this.intro = intro;
        this.area = area;
        this.leaderId = leaderId;
        this.formationName = formationName;
    }
}
