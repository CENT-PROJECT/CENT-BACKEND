package SPOTY.Backend.domain.user;

import SPOTY.Backend.domain.user.Role;
import SPOTY.Backend.global.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.util.UUID;


@Getter
@Entity
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(columnDefinition = "VARCHAR(100)")
    private String email;

    @Column(columnDefinition = "VARCHAR(100)")
    private String username;

    @Column(columnDefinition = "VARCHAR(100)")
    private String nickname;

    @Column(columnDefinition = "VARCHAR(100)")
    private String password;

    @Column(columnDefinition = "VARCHAR(100)")
    private String state;

    @Column(columnDefinition = "VARCHAR(100)")
    private String city;

    @Column(columnDefinition = "VARCHAR(50)")
    private String preferredPosition;

    @Column(columnDefinition = "VARCHAR(100)")
    private String isExpert;

    @Column(columnDefinition = "VARCHAR(100)")
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

