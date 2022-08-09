package goingmerry.cent.player;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlayerInfoDto {

    private Long playerId;
    private Long userId;
    private Long teamId;

    private String userName;// get User
    private String nickname;
    private String email;

    private String wantPosition; // Get Player
    private String currentPosition;
    private boolean already;
    private boolean isCaptain;


    @Builder
    public PlayerInfoDto(Player player) {
        this.playerId = player.getId();
        this.userId = player.getUser().getId();
        this.teamId = player.getTeam().getId();
        this.userName = player.getUser().getUsername();
        this.nickname = player.getUser().getNickName();
        this.email = player.getUser().getEmail();
        this.wantPosition = player.getUser().getPosition();
        this.currentPosition = player.getCurrent();
        this.already = player.isAlready();
        this.isCaptain = player.isCaptain();
    }
//    public PlayerInfoDto(Long playerId, Long userId, Long teamId, String userName, String email, String wantPosition, String currentPosition, boolean already, boolean isCaptain) {
//        this.playerId = playerId;
//        this.userId = userId;
//        this.teamId = teamId;
//        this.userName = userName;
//        this.email = email;
//        this.wantPosition = wantPosition;
//        this.currentPosition = currentPosition;
//        this.already = already;
//        this.isCaptain = isCaptain;
//    }
}
