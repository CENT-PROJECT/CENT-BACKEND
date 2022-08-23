package goingmerry.cent.player;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PlayerDto {

    private Long id;
    private Long userId;
    private Long teamId;
    private String back;
    private boolean already;
    private String current;
    private boolean isCaptain;
    private Integer formationIndex;

    @Builder
    public PlayerDto(Player entity) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.teamId = entity.getTeam().getId();
        this.back = entity.getBack();
        this.already = entity.isAlready();
        this.current = entity.getCurrent();
        this.isCaptain = entity.isCaptain();
        this.formationIndex = entity.getFormationIndex();
    }
}
