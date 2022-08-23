package goingmerry.cent.application;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationResDto {

    private Long fromUser; // 신청한 사람

    private Long toUser; // 받는 사람

    private String status; // status flag,승인 - A 거절 - D 대기 - W(default - W)

    private Long teamId;

    @Builder
    public ApplicationResDto(Application entity) {
        this.fromUser = entity.getFromUser().getId();
        this.toUser = entity.getToUser().getId();
        this.status = entity.getStatus();
        this.teamId = entity.getTeam().getId();
    }


}
