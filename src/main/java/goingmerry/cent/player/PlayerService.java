package goingmerry.cent.player;

import goingmerry.cent.team.Team;
import goingmerry.cent.team.TeamRepository;
import goingmerry.cent.user.User;
import goingmerry.cent.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    private final UserRepository userRepository;

    private final TeamRepository teamRepository;


    // 선수 저장
    public PlayerDto save(PlayerSaveDto playerInfo) {
        log.info("[service player_save call, save player name is {}]");

        Optional<User> user = userRepository.findById(playerInfo.getUserId());
        Optional<Team> team = teamRepository.findById(playerInfo.getTeamId());

        playerInfo.setAlready(false);
        playerInfo.setBack("0");
        playerInfo.setCaptain(false);

        Player entity = playerRepository.save(Player.builder()
                .user(user.get())
                .team(team.get())
                .back(playerInfo.getBack())
                .current(null)
                .already(playerInfo.isAlready())
                .formationIndex(null)
                .isCaptain(playerInfo.isCaptain())
                .build());

        return PlayerDto
                .builder()
                .entity(entity)
                .build();
    }

    // 전체 선수 목록 조회
    public List<PlayerDto> findAllPlayers() {

        List<PlayerDto> res = new ArrayList<>();

        List<Player> entity = playerRepository.findAll();

        for (Player player : entity) {
            res.add(PlayerDto.builder().entity(player).build());
        }

        log.debug("{}",res);

        return res;
    }

    // 선수 단일 조회
    public PlayerInfoDto find(Long playerId) {

        Optional<Player> optionalPlayer = playerRepository.findById(playerId);

        if(!optionalPlayer.isEmpty()) {
            Player entity = optionalPlayer.get(); // 선수 엔티티로 나머지 도메인 전체 조회

            return PlayerInfoDto
                    .builder()
                    .player(entity)
                    .build();
        } else {
//            throw new RuntimeException("존재하지 않는 선수입니다.");
            return null;
        }
    }

    public List<PlayerInfoDto> findAllByTeamId(Long teamId) {

        List<Player> entities = playerRepository.findByTeamId(teamId);
        List<PlayerInfoDto> res = new ArrayList<>();

        if(entities.isEmpty()) {
            return null;
        }

        for (Player entity : entities) {
            res.add(
                    PlayerInfoDto
                            .builder()
                            .player(entity)
                            .build());
        }

        return res;
    }

    // 등번호 업데이트
    @Transactional // 디비 영속성 이용해서 트랜젝션 연산, 리포지토리에 update 로직 넣지 않고 엔티티 클래스에서 업데이트 가능함.
    public Long update(Long id, String newNum) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 선수가 없습니다."));

        player.update(newNum);

        return id;
    }

    public int delete(Long playerId) {

        Optional<Player> player = playerRepository.findById(playerId);

        User user = player.get().getUser();
        user.update(null);

        if(player.isEmpty()) {
            return -102;
        } else {
            playerRepository.deleteById(player.get().getId());
            return 100;
        }

    }

    public List<PlayerDto> getPlayersByTeamId(Long teamId) {

        List<Player> playerEntities = playerRepository.findByTeamId(teamId);

        List<PlayerDto> res = new ArrayList<>();

        if(!playerEntities.isEmpty()) {
            for (Player playerEntity : playerEntities) {
                res.add(new PlayerDto(playerEntity));
            }
        }

        return res;
    }



}
