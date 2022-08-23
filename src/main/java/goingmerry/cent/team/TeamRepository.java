package goingmerry.cent.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

        Optional<Team> findByTeamName(String TeamName);

        @Query(value = "select team_name from team", nativeQuery = true)
        List<String> findTeamName();

        @Query(value = "SELECT p.team_name FROM team p WHERE p.area = :area", nativeQuery = true)
        List<String> findTeamNameByArea(@Param("area") String area);

        void deleteByTeamName(String teamName);

        @Modifying
        @Query("UPDATE FROM Team p SET p.logo = :logo WHERE p.id = :teamId")
        void updateLogo(@Param("teamId")Long teamId, @Param("logo")String logo);


}
