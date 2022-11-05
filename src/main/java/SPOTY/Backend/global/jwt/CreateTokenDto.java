package SPOTY.Backend.global.jwt;

import SPOTY.Backend.domain.user.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTokenDto {
    private UUID userId;
    private String email;
    private Role role;
}
