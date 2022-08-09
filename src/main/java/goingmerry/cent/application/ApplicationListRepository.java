package goingmerry.cent.application;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationListRepository extends JpaRepository<Application, Long> {
    //신청 정보 관리
//
//    @Query(value = "SELECT p.* FROM application_list p " +
//            "WHERE p.status = :status and p.team_name = :team_name", nativeQuery = true)
//    List<ApplicationList> findByTeamName(@Param("team_name")String teamName, String status);
//
//    List<ApplicationList> findByToEmail(String toEmail);
}
