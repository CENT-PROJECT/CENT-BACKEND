package goingmerry.cent.application;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationDto {

    private Long fromUser; // 신청한 사람

    private Long toUser; // 받는 사람

    private String status; // status flag,승인 - A 거절 - D 대기 - W(default - W)

    private Long teamId;

    @Builder
    public ApplicationDto(Long fromUser, Long toUser, String status, Long teamId) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.status = status;
        this.teamId = teamId;
    }


}
