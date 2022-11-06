package SPOTY.Backend.domain.user.domain;

import SPOTY.Backend.domain.user.dto.UserRequestDto;
import SPOTY.Backend.global.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.util.UUID;


@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private Role role = Role.ROLE_USER;

    public void setRole(Role role) {
        this.role = role;
    }

    public User(UserRequestDto.JoinRequestDto dto) {
        this.email = dto.getEmail();
        this.username = dto.getUsername();
        this.nickname = dto.getNickname();
        this.password = dto.getPassword();
        this.state = dto.getState();
        this.city = dto.getCity();
        this.preferredPosition = dto.getPreferredPosition();
        this.isExpert = dto.getIsExpert();
        this.birthDate = dto.getBirthDate();
    }
}

