package goingmerry.cent.service;

import goingmerry.cent.advice.exception.EmailAuthTokenNotFountException;
import goingmerry.cent.advice.exception.MemberEmailAlreadyExistsException;
import goingmerry.cent.advice.exception.MemberNotFoundException;
import goingmerry.cent.domain.EmailAuth;
import goingmerry.cent.domain.User;
import goingmerry.cent.dto.EmailAuthRequestDto;
import goingmerry.cent.dto.UserSaveDto;
import goingmerry.cent.jwt.JwtTokenProvider;
import goingmerry.cent.repository.EmailAuthRepository;
import goingmerry.cent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SignService {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final EmailAuthRepository emailAuthRepository;

    private final EmailService emailService;


    /**
     * Dto로 들어온 값을 통해 회원가입을 진행
     * @param requestDto
     * @return
     */
    @Transactional
    public void registerMember(UserSaveDto requestDto) {
        validateDuplicated(requestDto.getEmail());
        EmailAuth emailAuth = emailAuthRepository.save(
                EmailAuth.builder()
                        .email(requestDto.getEmail())
                        .authToken(UUID.randomUUID().toString())
                        .expired(false)
                        .build());

        System.out.println(emailAuth.getExpired());
        User user = userRepository.save(
                User.builder()
                        .email(requestDto.getEmail())
                        .password(passwordEncoder.encode(requestDto.getPassword()))
                        .emailAuth(false)
                        .build());

        emailService.send(emailAuth.getEmail(), emailAuth.getAuthToken());
    }

    public void validateDuplicated(String email) {
        if (userRepository.findByEmail(email).isPresent())
            throw new MemberEmailAlreadyExistsException();
    }

    /**
     * 이메일 인증 성공
     * @param requestDto
     */
    @Transactional
    public void confirmEmail(EmailAuthRequestDto requestDto) {
        EmailAuth emailAuth = emailAuthRepository.findValidAuthByEmail(requestDto.getEmail(), requestDto.getAuthToken());
//                .orElseThrow(EmailAuthTokenNotFountException::new);
        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(MemberNotFoundException::new);
        emailAuth.useToken();
        user.setEmailAuth(true);
    }

    /**
     * 로컬 로그인 구현
     * @param requestDto
     * @return
     */
//        @Transactional
//        public MemberLoginResponseDto loginMember(MemberLoginRequestDto requestDto) {
//            Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(MemberNotFoundException::new);
//            if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword()))
//                throw new LoginFailureException();
//            if (!member.getEmailAuth())
//                throw new EmailNotAuthenticatedException();
//            member.updateRefreshToken(jwtTokenProvider.createRefreshToken());
//            return new MemberLoginResponseDto(member.getId(), jwtTokenProvider.createToken(requestDto.getEmail()), member.getRefreshToken());
//        }



//        /**
//         * 토큰 재발행
//         * @param requestDto
//         * @return
//         */
//        @Transactional
//        public TokenResponseDto reIssue(TokenRequestDto requestDto) {
//            if (!jwtTokenProvider.validateTokenExpiration(requestDto.getRefreshToken()))
//                throw new InvalidRefreshTokenException();
//
//            Member member = findMemberByToken(requestDto);
//
//            if (!member.getRefreshToken().equals(requestDto.getRefreshToken()))
//                throw new InvalidRefreshTokenException();
//
//            String accessToken = jwtTokenProvider.createToken(member.getEmail());
//            String refreshToken = jwtTokenProvider.createRefreshToken();
//            member.updateRefreshToken(refreshToken);
//            return new TokenResponseDto(accessToken, refreshToken);
//        }
//
//        public Member findMemberByToken(TokenRequestDto requestDto) {
//            Authentication auth = jwtTokenProvider.getAuthentication(requestDto.getAccessToken());
//            UserDetails userDetails = (UserDetails) auth.getPrincipal();
//            String username = userDetails.getUsername();
//            return memberRepository.findByEmail(username).orElseThrow(MemberNotFoundException::new);
//        }
//    }

}
