package goingmerry.cent.team;

import goingmerry.cent.BaseTimeEntity;
import goingmerry.cent.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Team extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String teamName;

    @Column(nullable = true, length = 64)
    private String logo;

    private String intro;

    @Column(nullable = false)
    private String area;
//    @Enumerated(EnumType.STRING)
//    private Area area;

    private String formationName;

    // user 1:1
    @OneToOne(mappedBy = "team")
    private User user;

    // user 1:1, teamLeader : 팀장
    @OneToOne
    @JoinColumn(name = "leader_id")
    private User leaderUser;

    @Builder
    public Team(String teamName, String logo, String intro, String area, String formationName, User leaderUser) {

        this.teamName = teamName;
        this.logo = logo;
        this.intro = intro;
        this.area = area;
        this.formationName = formationName;
        this.leaderUser = leaderUser;
    }

    public void update(String intro, String logo) {
        this.intro = intro;
        this.logo = logo;
    }

    public void update(String formationName) {
        this.formationName = formationName;
    }
}
