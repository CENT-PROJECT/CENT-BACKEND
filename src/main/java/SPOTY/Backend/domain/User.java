package SPOTY.Backend.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.UUID;


@Getter
@Entity
@NoArgsConstructor
public class User {

    @Id
    private String id;

    @Column
    private String email;

    @Column
    private String username;

    @Column
    private String nickname;

    @Column
    private String password;

    @Column
    private String state;

    @Column
    private String city;

    @Column
    private String preferredPosition;

    @Column
    private String isExpert;

    @Column
    private String birthDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void setRole(Role role) {
        this.role = role;
    }

    @Builder
    public User(String email, String username, String nickname,
                String password, String state, String city, String preferredPosition,
                String isExpert, String birthDate) {
        this.id = UUID.randomUUID().toString();
        this.email = email;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.state = state;
        this.city = city;
        this.preferredPosition = preferredPosition;
        this.isExpert = isExpert;
        this.birthDate = birthDate;
    }
}

