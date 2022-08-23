package goingmerry.cent.player;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

//    int countPlayersByTeamName(String teamName);

    @Query(value = "SELECT * FROM player WHERE user_id = :userId", nativeQuery = true)
    Optional<Player> findByUserId(@Param("userId")Long userId);

    @Query(value = "SELECT * FROM player WHERE team_id = :team_id", nativeQuery = true)
    List<Player> findByTeamId(@Param("team_id")Long teamId);

}
