package SPOTY.Backend.domain.user.dto;

import SPOTY.Backend.domain.user.domain.Role;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private UUID id;
    private String email;
    private String username;
    private String nickname;
    private String password;
    private String state;
    private String city;
    private String preferredPosition;
    private String isExpert;
    private String birthDate;
    private Role role = Role.ROLE_USER;
    private String providerType;
    private String providerId;
}
