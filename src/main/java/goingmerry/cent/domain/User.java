package goingmerry.cent.domain;

import lombok.*;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String email;

    private String activityArea;

    private String Position;

    private boolean isExpert;

    private String gender;

    private String ageRange;

    private String birth;

    private String userImage;

    private String userId;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String userId, SocialType socialType, Role role) {
        this.userId = userId;
        this.socialType = socialType;
        this.role = role;
    }
}
