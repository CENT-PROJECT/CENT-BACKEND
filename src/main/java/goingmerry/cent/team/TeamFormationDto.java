package goingmerry.cent.team;

import goingmerry.cent.player.PlayerDto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class TeamFormationDto {

    private Long teamId;

    private String formationName;

    private List<PlayerDto> playerList;

    @Builder
    public TeamFormationDto(Long teamId, String formationName, List<PlayerDto> playerList) {
        this.teamId = teamId;
        this.formationName = formationName;
        this.playerList = playerList;
    }
}
