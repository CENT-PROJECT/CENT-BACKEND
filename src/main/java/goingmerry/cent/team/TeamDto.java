package goingmerry.cent.team;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeamDto {

    private Long id;

    private String teamName;

    private String logo;

    private String intro;

    private String area;

    private Long leaderId;

    private String formationName;

    @Builder
    public TeamDto(Team entity) {
        this.id = entity.getId();
        this.teamName = entity.getTeamName();
        this.logo = entity.getLogo();
        this.intro = entity.getIntro();
        this.area = entity.getArea();
        this.leaderId = entity.getLeaderUser().getId();
        this.formationName = entity.getFormationName();
    }
}
