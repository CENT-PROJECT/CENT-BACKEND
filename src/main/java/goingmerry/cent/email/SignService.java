package goingmerry.cent.email;

import goingmerry.cent.user.Role;
import goingmerry.cent.user.User;
import goingmerry.cent.user.UserDto;
import goingmerry.cent.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final UserRepository userRepository;
    private final EmailAuthRepository emailAuthRepository;
    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    @Transactional
    public void join(UserDto.UserSaveDto dto) throws Exception {
        //redis 를 사용하던가 , 다른 방법을 알아봐야됨 , 만료기간 지날시, 로그인이 안되니가..
        validateDuplicated(dto.getEmail());
        EmailAuth emailAuth = emailAuthRepository.save(
                EmailAuth.builder()
                        .email(dto.getEmail())
                        .authToken(UUID.randomUUID().toString())
                        .expired(false)
                        .build());


        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        //id 막 생기는 이슈. email 중복확인 검사 필요하긴 할듯.
        userRepository.save(new User(dto));
        emailService.send(emailAuth.getEmail(), emailAuth.getAuthToken());
    }

    //해당 이메일로 가입된 계정이 있는지 검증
    public void validateDuplicated(String email) throws Exception {
        if (userRepository.findByEmail(email).isPresent())
            throw new Exception("해당 이메일이 존재합니다.");
    }

    /**
     * 이메일 인증 성공
     * @param requestDto
     */
    @Transactional
    public void confirmEmail(EmailAuthRequestDto requestDto) throws Exception{
        Optional<EmailAuth> emailAuth = emailAuthRepository.findValidAuthByEmail(requestDto.getEmail(), requestDto.getAuthToken(), LocalDateTime.now());
        Optional<User> user = userRepository.findByEmail(requestDto.getEmail());
        emailAuth.get().useToken();
        user.get().setRole(Role.USER);
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
