package goingmerry.cent.domain;

import goingmerry.cent.dto.UserSaveDto;
import goingmerry.cent.dto.UserUpdateDto;
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

    private String nickName;

    private String email;

    private String activityArea = "";

    private String position;

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

//    @OneToOne
//    @JoinColumn(name = "team_id")
//    private Team team



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

    public void additionalInfo(UserSaveDto userSaveDto) {
        this.nickName = userSaveDto.getNickName();
        this.activityArea = userSaveDto.getActivityArea();
        this.position = userSaveDto.getPosition();
        this.isExpert = userSaveDto.isExpert();
    }

    public void update(UserUpdateDto dto){
        this.nickName = dto.getNickName();
        this.activityArea = dto.getActivityArea();
        this.ageRange = dto.getAgeRange();
        this.position = dto.getPosition();
        this.isExpert = dto.isExpert();
    }
}
