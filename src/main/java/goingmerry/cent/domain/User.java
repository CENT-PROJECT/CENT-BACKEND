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

    private Long userId;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String userName, String email, String gender, String ageRange, String birth, String userImage, Long userId, SocialType socialType, Role role) {
        this.userName = userName;
        this.email = email;
        this.gender = gender;
        this.ageRange = ageRange;
        this.birth = birth;
        this.userImage = userImage;
        this.userId = userId;
        this.socialType = socialType;
        this.role = role;
    }
}
