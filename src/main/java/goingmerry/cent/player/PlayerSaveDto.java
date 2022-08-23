package goingmerry.cent.player;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerSaveDto {

    private Long userId;

    private Long teamId;

    private boolean already;

    private boolean isCaptain;

    private String back;

    @Builder
    public PlayerSaveDto(Long userId, Long teamId, boolean already, boolean isCaptain, String back) {
        this.userId = userId;
        this.teamId = teamId;
        this.already = already;
        this.isCaptain = isCaptain;
        this.back = back;
    }
}
