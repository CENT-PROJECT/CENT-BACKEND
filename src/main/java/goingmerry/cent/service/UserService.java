package goingmerry.cent.service;

import goingmerry.cent.domain.User;
import goingmerry.cent.dto.UserUpdateDto;
import goingmerry.cent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void update(UserUpdateDto dto){
        Optional<User> user = userRepository.findById(dto.getId());
        user.get().update(dto);
    }
}
