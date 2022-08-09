package goingmerry.cent.player;

import goingmerry.cent.team.Team;
import goingmerry.cent.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Player {
    /*
선수정보 :
선수이메일 : email
등번호 : back
이름 : name
배치여부 : already
선호포지 : like
실배치포지 : select
팀명 : teamName
주장여부 : leader
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String back;

    @Column(nullable = false)
    private boolean already;

    //like, select 는 데이터베이스의 예약어이기때문에 sql 문이 오류가 난다.
    //-> want, current 로 변경
    private String current;

    private boolean isCaptain; // 주장여부

    @Column(nullable = true)
    private Integer formationIndex; // 현재 원 번호? -> 선발 선수들

//    User : Player = 1:1

//    @Column(nullable = false)
//    private String name; // 유저아이디로 합쳐져 필요 x
//    @Column(nullable = false)
//    private String userEmail; // 유저아이디로 통합할 것
//    private String want; // 유저테이블에서 가져올 것
//    @Column(nullable = false)
//    private String teamName; // 팀아이디로 통합할 것

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(optional = true)
    @JoinColumn(name = "teamId")
    private Team team;


    public Team getTeam() {
        return team;
    }

//    public void setTeam(Team team) {
//        this.team = team;
//    }

    @Builder
    public Player(String back, boolean already, String current, boolean isCaptain, Integer formationIndex, User user, Team team) {
        this.back = back;
        this.already = already;
        this.current = current;
        this.isCaptain = isCaptain;
        this.formationIndex = formationIndex;
        this.user = user;
        this.team = team;
    }


    public void update(String back) {
        this.back = back;
    }

    public void update(PlayerDto req) {
        this.back = req.getBack();
        this.already = req.isAlready();
        this.current = req.getCurrent();
        this.isCaptain = req.isCaptain();
        this.formationIndex = req.getFormationIndex();
    }

}



