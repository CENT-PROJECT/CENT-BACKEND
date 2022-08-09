package goingmerry.cent.player;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/player")
public class PlayerController {
/*
선수 관리 페이지에서 이루어질 Api
 */

    private final PlayerService playerService;

    private final PlayerRepository playerRepository;

    // 새로운 선수를 저장한다.
    // teamid, userid?
    @PostMapping(value = "/save")
    public ResponseEntity<PlayerDto> save(@RequestBody PlayerSaveDto playerInfo) {

        log.info("[API CALL] : /api/player/save, save user Id is {}", playerInfo.getUserId());

        if(!playerRepository.findByUserId(playerInfo.getUserId()).isEmpty()) {
            log.error("is already join this team! ");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        PlayerDto res = playerService.save(playerInfo);

        return new ResponseEntity(res, HttpStatus.OK);
    }

    // 전체 선수 목록 get
    @GetMapping(value = "/getAll")
    public ResponseEntity<List<PlayerDto>> findAllPlayers() {

        log.info("[API CALL] : /api/player/getuser");

        List<PlayerDto> res = playerService.findAllPlayers();

        log.debug(res.toString());

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // player 단일 선수 조회
    @GetMapping(value = "/find")
    public ResponseEntity findUserById(@RequestParam Long playerId) {

        PlayerInfoDto res = playerService.find(playerId);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PatchMapping(value = "/patchBackNum")
    public ResponseEntity patchBackNum(@RequestParam Long playerId, String newNum) {
        log.info("[API CALL] : /api/player/patchBackNum");

        Long patchId = playerService.update(playerId, newNum);
        return new ResponseEntity<>(patchId, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public Map<String, Object> delete(@RequestParam Long playerId) {

        int code = playerService.delete(playerId);
        var res = new HashMap<String, Object>();

        if(code == -102) {
            res.put("code", code);
            res.put("Massage", "선수 아이디가 유효하지 않습니다.");
        } else if (code == 100) {
            res.put("code", code);
            res.put("Massage", "선수 삭제 완료");
        }
        return res;
    }

    // 특정 팀의 선수 관리 목록
    @GetMapping(value = "/get")
    public ResponseEntity<List<PlayerInfoDto>> getAllPlayers(@RequestParam Long teamId) {

        /*

         */

        log.info("[API CALL] : /api/player/findplayers/{}",teamId);

        List<PlayerInfoDto> players;

        players = playerService.findAllByTeamId(teamId);

        if(players.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(players, HttpStatus.OK);

    }

    // 팀이름 받아서 해당 팀 선수 명단 불러와준다. 선수 관리 페이지 x 팀 정보 페이지.
    @GetMapping(value = "/playerList")
    public ResponseEntity<List<PlayerDto>> formationPlayerInfo(@RequestParam Long teamId) {

        log.info("[API CALL] : /api/player/playerInfo, teamId={}", teamId);
        List<PlayerDto> res = playerService.getPlayersByTeamId(teamId);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }




}
