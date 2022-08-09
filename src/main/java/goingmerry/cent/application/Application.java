package goingmerry.cent.application;

import goingmerry.cent.BaseTimeEntity;
import goingmerry.cent.team.Team;
import goingmerry.cent.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Application extends BaseTimeEntity {

    // 신청 목록

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false)
//    private String fromEmail; // 신청한 사람 // foreign key
//
//    @Column(nullable = false)
//    private String toEmail; // 받는 사람 // foreign key

    @Column(nullable = false)
    private String status; // status flag,승인 - A 거절 - D 대기 - W(default - W)

//    private String teamName; // foreign key

    @ManyToOne
    @JoinColumn(name = "from_user")
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user")
    private User toUser;

    @ManyToOne
    @JoinColumn(name = "teamId")
    private Team team;

    public User getFromUser() {
        return fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public Team getTeam() {
        return team;
    }

    @Builder
    public Application(Long id, String status, User fromUser, User toUser, Team team) {
        this.id = id;
        this.status = status;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.team = team;
    }

    public void update(String status) {
        this.status = status;
    }
}
