package SPOTY.Backend.domain.user.service;

import SPOTY.Backend.domain.mail.MailService;
import SPOTY.Backend.domain.user.domain.Role;
import SPOTY.Backend.domain.user.domain.User;
import SPOTY.Backend.domain.user.dto.UserRequestDto;
import SPOTY.Backend.domain.user.dto.UserResponseDto;
import SPOTY.Backend.domain.user.repository.UserRepository;
import SPOTY.Backend.global.exception.domain.user.ConflictUser;
import SPOTY.Backend.global.exception.domain.user.UnAuthorizedUser;
import SPOTY.Backend.global.jwt.CreateTokenDto;
import SPOTY.Backend.global.jwt.TokenService;
import SPOTY.Backend.global.util.OptionalUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    @Lazy
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final UserRepository userRepository;
    private final OptionalUtil<User> optionalUtil;
    private final TokenService tokenService;

    public void checkDuplicatedUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            throw new ConflictUser();
        }
    }

    public boolean checkVerifiedEmail(String email, String code) {
        //mail code 확인.
        mailService.checkVerifiedEmail(email, code);

        return true;
    }

    public void join(UserRequestDto.JoinRequestDto dto) {

        //중복 ID 확인.
        checkDuplicatedUser(dto.getEmail());

        //mail code 확인.
        mailService.checkVerifiedEmail(dto.getEmail(), dto.getCode());

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(new User(dto));
    }

    // 회원가입 - OAuth2 이용
    public void oauth2Join(UserRequestDto.SocialJoinRequestDto dto) {

        // email 확인, provider, role 확인

        Optional<User> optionalUser = userRepository.findById(dto.getId());

        User entity = optionalUser.get();
        entity = User.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .role(Role.ROLE_USER)
                .username(entity.getUsername())
                .providerType(entity.getProviderType())
                .providerId(entity.getProviderId())
                .birthDate(entity.getBirthDate())
                .state(dto.getState())
                .city(dto.getCity())
                .nickname(dto.getNickname())
                .preferredPosition(dto.getPreferredPosition())
                .build();

        User user = userRepository.save(entity); // 이렇게 하면 그냥 덮어씌워짐. 해결 필요
        log.info("Social join user : {}", user.getEmail());

    }

    public UserResponseDto.LoginResponseDto login(UserRequestDto.LoginRequestDto dto) {
        Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());
        optionalUtil.ifEmptyThrowError(optionalUser, new UnAuthorizedUser());
        User user = optionalUser.get();

        if (passwordEncoder.matches(dto.getPassword(), optionalUser.get().getPassword())) {
            CreateTokenDto createTokenDto = new CreateTokenDto(
                    user.getId(), user.getEmail(), user.getRole(), user.getProviderType());
            String accessToken = tokenService.createAccessToken(createTokenDto);
            String refreshToken = tokenService.createAccessToken(createTokenDto);
            return new UserResponseDto.LoginResponseDto(accessToken, refreshToken);
        }
        throw new UnAuthorizedUser();
    }

}
