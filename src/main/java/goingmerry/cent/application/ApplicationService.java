package goingmerry.cent.application;

import goingmerry.cent.team.Team;
import goingmerry.cent.team.TeamRepository;
import goingmerry.cent.user.User;
import goingmerry.cent.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationListRepository applicationListRepository;

    private final UserRepository userRepository;

    private final TeamRepository teamRepository;


    // 유저 -> 팀 신청
    public ApplicationResDto applicationToTeam(ApplicationDto req) {

        Optional<User> fromUser = userRepository.findById(req.getFromUser());
//        Optional<User> toUser = userRepository.findById(req.getToUser());
        Optional<Team> team = teamRepository.findById(req.getTeamId());


        if(fromUser.isEmpty() || team.isEmpty()) {
            throw new RuntimeException("bad request !!");
        }

        Application entity = Application
                .builder()
                .fromUser(fromUser.get())
                .toUser(team.get().getLeaderUser()) // 해당 팀의 팀장 id 넣어줌.
                .team(team.get())
                .status("W")
                .build();

        applicationListRepository.save(entity);

       ApplicationResDto res = ApplicationResDto
               .builder()
               .entity(entity)
               .build();

        return res;
    }



    // 승인 이후 팀 가입까지 시켜줘야 한다.
    public ApplicationResDto approvalTeam(Long applicationId, Long leaderId) {


        Long id = updateStatus(applicationId, "A");

        Optional<Application> entity = applicationListRepository.findById(id);

        ApplicationResDto res = ApplicationResDto
                .builder()
                .entity(entity.get())
                .build();

        return res;
    }

    @Transactional
    private Long updateStatus(Long applicationId, String status) {

        Application entity = applicationListRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 신청 현황이 없습니다."));

        entity.update(status);

        return entity.getId();
    }

//
//    // user -> team get
//    // 신청 대기 상태 목록만 보는 것이기 때문에 status = W 조건을 붙여줘야 한다.
//    public List<ApplicationListDto> getApplicationList_team(String teamName) {
//
//        List<ApplicationListDto> applicationList = new ArrayList<>();
//        List<ApplicationList> listEntity = applicationListRepository.findByTeamName(teamName, "W");
//
//        for(int i=0;i<listEntity.size();i++) {
//
//            applicationList.add(ApplicationListDto
//                    .builder()
//                    .entity(listEntity.get(i))
//                    .build());
//
//            log.info("@teamName : {}, Email : {}",listEntity.get(i).getTeamName(),listEntity.get(i).getFromEmail());
//        }
//
//        return applicationList;
//    }
//
//    // 팀 -> 유저 신청 정보 get
//    public List<ApplicationListDto> getApplicationList_user(String email) {
//
//        List<ApplicationListDto> applicationList = new ArrayList<>();
//        List<ApplicationList> listEntity = applicationListRepository.findByToEmail(email);
//
//        for(int i=0;i<listEntity.size();i++) {
//            applicationList.add(ApplicationListDto
//                    .builder()
//                    .entity(listEntity.get(i))
//                    .build());
//
//        }
//        return applicationList;
//    }
//
//
//    // 팀장이 선수를 찾기 위해서 검색
////    public List<UserDto> getUserInfo(String infoString) {
////
////        List<User> listEntity = userRepository.findUserInfo(infoString);
////        List<UserDto> infoList = new ArrayList<>();
////
////        for(int i=0;i<listEntity.size();i++) {
////            infoList.add(UserDto
////                    .builder()
////                    .entity(listEntity.get(i))
////                    .build());
////        }
////
////        return infoList;
////    }
//
//
//
//
//    public ApplicationList ApplyToEntity(String fromEmail, String toEmail, String teamName) {
//
//        return ApplicationList
//                .builder()
//                .fromEmail(fromEmail)
//                .toEmail(toEmail)
//                .status("W") // 신청 시 기본값, ==Wait
//                .teamName(teamName)
//                .build();
//    }



}
