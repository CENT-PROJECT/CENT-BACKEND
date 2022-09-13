package goingmerry.cent.user;

import goingmerry.cent.BaseTimeEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User extends BaseTimeEntity implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //DB 저장 인덱스

    private String userName;

    private String nickName;

    private String email;   //ID

    private String password;    //Password

    private String activityArea;

    private String position;

    private boolean isExpert;

    private String gender;

    private String birthDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    //연관관계 주인
    // team 삽입 , 삭제 , 수정 다 가능.
//    @OneToOne
//    @JoinColumn(name = "team_id")
//    private Team team;

    public void setRole(Role role) {
        this.role = role;
    }

    @Builder
    public User(UserDto.UserSaveDto dto) {
//        this.userName = dto.getUserName();
//        this.nickName = dto.getNickName();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
//        this.activityArea = dto.getActivityArea();
//        this.position = dto.getPosition();
//        this.isExpert = dto.isExpert();
//        this.gender = dto.getGender();
//        this.birthDate = dto.getBirthDate();
        this.role = Role.UNVERIFIED_USER;
    }


    //unique 한 값, 추후 token 만들때 사용.
    @Override
    public String getUsername() {
        return email;
    }

    public String getDisplayName() {
        return nickName + "<" + userName + ">";
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(role.getGrantedAuthority()));

        return authorities;
    }

}
