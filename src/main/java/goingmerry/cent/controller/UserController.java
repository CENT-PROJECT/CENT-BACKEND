package goingmerry.cent.controller;

import goingmerry.cent.domain.User;
import goingmerry.cent.dto.LoginResponseDto;
import goingmerry.cent.dto.UserSaveDto;
import goingmerry.cent.jwt.JwtTokenResolver;
import goingmerry.cent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final JwtTokenResolver jwtTokenResolver;
    private final UserRepository userRepository;

    @GetMapping("/")
    public ResponseEntity<LoginResponseDto> userFirstJoinOrLogin(@RequestHeader String Authorization){
        
        Long id = jwtTokenResolver.getId(Authorization);
        Optional<User> user = userRepository.findById(id);
        LoginResponseDto dto = new LoginResponseDto(Authorization);
        if(user.get().getActivityArea().isEmpty()){
            dto.setJoined(false);
            return new ResponseEntity<LoginResponseDto>(dto, HttpStatus.OK);
        }else{
            return new ResponseEntity<LoginResponseDto>(dto, HttpStatus.OK);
        }
    }

    @PostMapping("/join")
    public ResponseEntity<String> userSecondJoin(@RequestHeader String Authorization, @RequestBody UserSaveDto userSaveDto){
        Long id = jwtTokenResolver.getId(Authorization);
        Optional<User> user = userRepository.findById(id);
        user.get().additionalInfo(userSaveDto);

        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

}
