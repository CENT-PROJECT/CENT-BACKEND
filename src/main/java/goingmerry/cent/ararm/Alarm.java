package goingmerry.cent.ararm;

import goingmerry.cent.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Alarm extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromEmail; // 누가 // 유저아이디로 통합할 것

    // notnull
    @Column(nullable = false)
    private String toEmail; // 누구에게 // 유저아이디로 통합할 것

    private String teamName; // 어떤 팀에서~ // 팀아이디로 통합할 것

    // notnull
    @Column(nullable = false)
    private String value; // 1. 팀 관련 이벤트 : "팀 가입 신청", "가입 거절", "가입 권유", "일정 등록"
                          // , "일정 등록" 등등
    // 각각 이벤트에 해당하는 플래그 만들기
    /*
        기본 : 대기 - W
        가입 신청 - A(APPLICATION)
        거절 - R(REFUSE)
        권유 - I(INVITATION)
     */

    @Builder
    public Alarm(String fromEmail, String toEmail, String teamName, String value) {
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.teamName = teamName;
        this.value = value;
    }


    // 시간 컬럼 따로 빼서 보낼 수 있는지 확인하기
    // 시간컬럼 못 빼면 따로 하나 생성해서 알려드리기

    /*
    * 알람 save / get / delete
    * */

    // get : 알람 전체 리스트(해당하는 toEmail로 뽑아서)

    // save : ex1_선수가 팀에 신청 보내는 경우) fromE-(신청 보내는 선수수), toE(팀장), value(신청 flag)
    // ex2_팀장이 영입신청 보내는 경우) toE-신청 받은 선수(유저)

    // 알람은 확인시 삭제 -> 클릭하면 해당 알림 삭제
    // 선수가 팀에 가입 했을 때 신청목록에선 날린다.
    // 알람에서 삭제는 toEmail, value 같은거 다 날리는 걸로 -> delete controller 에서 해결해야 한다.

    // 거절했을 경우엔 거절했다는 알림 1, 그와 동시에 신청 건은 삭제 요청도 해야 함
    // -> 거절 플래그를 달고 오면 거절한 대상의 알람은 삭제함 -> 동시에 새로운 거절했다는 알람 하나 생성해서 알람 테이블에 집어넣기
}
