package SPOTY.Backend.global.security;

import SPOTY.Backend.domain.user.domain.User;
import SPOTY.Backend.domain.user.repository.UserRepository;
import SPOTY.Backend.global.exception.domain.user.NotFoundUser;
import SPOTY.Backend.global.util.OptionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;
import java.util.Optional;


@Transactional
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final OptionalUtil optionalUtil;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        optionalUtil.ifEmptyThrowError(optionalUser, new NotFoundUser());

        User user = optionalUser.get();
        return new CustomUserDetails(user.getId(), user.getRole().toString());
    }

}
