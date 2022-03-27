package goingmerry.cent.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String email;

    private String activityArea;

    private String Position;

    private boolean isExpert;

    private String socialId;

    private String socialType;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String userName, String email, String socialId, String socialType, Role role) {
        this.userName = userName;
        this.email = email;
        this.socialId = socialId;
        this.socialType = socialType;
        this.role = role;
    }
}
