package SPOTY.Backend.domain.user.service;

import SPOTY.Backend.domain.mail.MailService;
import SPOTY.Backend.domain.user.domain.User;
import SPOTY.Backend.domain.user.dto.UserRequestDto;
import SPOTY.Backend.domain.user.dto.UserResponseDto;
import SPOTY.Backend.domain.user.repository.UserRepository;
import SPOTY.Backend.global.exception.domain.user.ConflictUser;
import SPOTY.Backend.global.exception.domain.user.UnAuthorizedUser;
import SPOTY.Backend.global.jwt.TokenService;
import SPOTY.Backend.global.util.OptionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final UserRepository userRepository;
    private final OptionalUtil<User> optionalUtil;
    private final TokenService tokenService;

    private void checkDuplicatedUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            throw new ConflictUser();
        }
    }

    public void join(UserRequestDto.JoinRequestDto dto) {

        //중복 ID 확인.
        checkDuplicatedUser(dto.getEmail());

        //mail code 확인.
        mailService.checkVerifiedEmail(dto.getEmail(), dto.getCode());

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(new User(dto));
    }

    public UserResponseDto.LoginResponseDto login(UserRequestDto.LoginRequestDto dto) {
        Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());
        optionalUtil.ifEmptyThrowError(optionalUser, new UnAuthorizedUser());
        String token = tokenService.encode(optionalUser.get());

        return new UserResponseDto.LoginResponseDto(token);
    }

}
