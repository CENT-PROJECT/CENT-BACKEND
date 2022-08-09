package goingmerry.cent.team;


import goingmerry.cent.config.FileUploadUtil;
import goingmerry.cent.player.Player;
import goingmerry.cent.player.PlayerDto;
import goingmerry.cent.player.PlayerRepository;
import goingmerry.cent.player.PlayerService;
import goingmerry.cent.user.User;
import goingmerry.cent.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    private final PlayerRepository playerRepository;

    private final UserRepository userRepository;

    private final PlayerService playerService;

    public boolean isLeaderExist(Long userId) {

        Optional<User> entity = userRepository.findById(userId);

        if(entity.isEmpty()) return true;
        else {
            return entity.get().getTeam() != null;
        }
    }

    //팀명 조회해서 있는지 없는지 판단해주는 메소드
    public boolean isExistTeam(String teamName){
        if(teamRepository.findByTeamName(teamName).isEmpty()){
            log.info("Not Exist Team");
            return false;
        }
        else {
            log.info("Exist TeamName : {}", teamName);
            return true;
        }
    }

    //존재하지 않던 팀의 새로운 생성. 팀 정보 받아서 디비에 저장해주는 역할을 한다.
    @Transactional
    public TeamDto create(TeamSaveDto req) {

        req.setFormationName("4421"); // default formationName = 4421;

        Team entity = toEntity(req);

        teamRepository.save(entity);

        TeamDto res = TeamDto.builder()
                .entity(entity).build();

        User leaderUser = userRepository.getById(res.getLeaderId());
        leaderUser.update(entity);

        return res;
    }

    public Team toEntity(TeamSaveDto req) {
        Optional<User> leader = userRepository.findById(req.getLeaderId());
        return Team
                .builder()
                .teamName(req.getTeamName())
                .leaderUser(leader.get())
                .area(req.getArea())
                .formationName(req.getFormationName())
                .intro(req.getIntro())
                .logo(req.getLogo())
                .build();
    }

    public Map<String, Boolean> isRequireValueNull(TeamSaveDto teamInfo) {

        Map<String, Boolean> resultMap = new HashMap<>();

        String teamName = teamInfo.getTeamName();
        String area = teamInfo.getArea();

        if(teamName.equals("")) {
            resultMap.put("teamName", true);
        }
        else {
            resultMap.put("teamName", false);
        }

        if (area.equals("")) {
            resultMap.put("area", true);
        }
        else {
            resultMap.put("area", false);
        }


        return resultMap;
    }

    public TeamDto teamInfo(Long teamId) {

        Optional<Team> entity = teamRepository.findById(teamId);

        if(!entity.isEmpty()) {

            return TeamDto
                    .builder()
                    .entity(entity.get())
                    .build();

        } else {
            return null;
        }

    }

    @Transactional
    public TeamDto update(TeamDto req) {

        Long teamId = req.getId();
        String intro = req.getIntro();
        String logo = req.getLogo();

        Optional<Team> entity = teamRepository.findById(teamId);
        if(entity.isEmpty()) {
            Team team = entity.get();

            team.update(intro, logo);
        } else {
            throw new RuntimeException("해당 팀 존재 x");
        }

        return TeamDto
                .builder()
                .entity(entity.get())
                .build();
    }

    @Transactional
    public void deleteTeam(Long teamId) {

        //팀 삭제 시 존재하는 팀을 삭제하는가는 일단 고려하지 않았다. 팀장 한명당 팀 정보는 하나밖에 없을 테니까.

        Optional<Team> entity = teamRepository.findById(teamId);
        User leader = entity.get().getUser();
        leader.update(null); // 팀장 유저의 연결관계 삭제

        teamRepository.delete(entity.get());

        // 팀 삭제 시 해당 팀의 로고도 함께 삭제해야 함.
        String uploadDir = "resources/static/logo/";
        String logoName = entity.get().getLogo();

        File file = new File(uploadDir + logoName);

        if(file.exists()) {

            if(!file.delete()) {

                //exception;
                log.error("file delete fail!");

            }

        } else {
            log.info("로고가 존재하지 않습니다.");
        }

    }

    public TeamFormationDto getFormation(Long teamId) {

        TeamFormationDto res = new TeamFormationDto();

        Team teamEntity = teamRepository.getById(teamId);

        String formationName = teamEntity.getFormationName();

        res.setTeamId(teamId);
        res.setFormationName(formationName);

        List<PlayerDto> playerRes = playerService.getPlayersByTeamId(teamId);

        res.setPlayerList(playerRes);

        return res;
    }

    // 해당 어노테이션 없으면 update 로직 안 돌아감.
    // 팀 포메이션명, 선수 already, current, formationIndex update
    @Transactional
    public TeamFormationDto updateFormation(TeamFormationDto req) {

        Long teamId = req.getTeamId();
        String formationName = req.getFormationName();

        // team get
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        Team teamEntity = optionalTeam.get();

        // team update formationName;
        teamEntity.update(formationName);

        // get players;
        List<PlayerDto> players = req.getPlayerList();

        // player update
        for (PlayerDto player : players) {
            Player entity = playerRepository.getById(player.getId());

            entity.update(player);

        }

        return req;
    }

    // 로고 이미지 관련

    @Transactional
    public TeamDto updateLogo(MultipartFile logo, Long teamId, String extension) throws IOException {


        Optional<Team> OpEntity = teamRepository.findById(teamId);

        String teamName = OpEntity.get().getTeamName();

        // 데이터베이스의 아이디를 받아와서 확장자와 연결, 저장 파일명을 만든다.
        String fileName = teamName + "." + extension;

        teamRepository.updateLogo(teamId, fileName);


//        String uploadDir = "src/main/resources/static/logo/";
        String uploadDir = "resources/static/logo/"; // 경로 관련해서 지워질 텐데 어떻게 할 지 경로 지정 성록이형
        // 파일은 같은 이름일 때 알아서 덮어 씌운다.
        FileUploadUtil.saveNewImage(uploadDir, fileName, logo);

        return TeamDto
                .builder()
                .entity(teamRepository.getById(teamId))
                .build();
    }

    public TeamLogoDto getLogo(Long teamId) {

        String uploadDir = "resources/static/logo/";
        Optional<Team> entity = teamRepository.findById(teamId);

        TeamLogoDto res = new TeamLogoDto();

        if(!entity.isEmpty()) {

            String logoName = entity.get().getLogo();
            Resource resource = new FileSystemResource(uploadDir + logoName);

            res.setResource(resource);
            res.setFilePath(uploadDir + logoName);

            return res;

        } else {
            return res;
        }

    }


    public String getExtension(String fileName) {

        String allowPattern = ".+\\.(jpg|png|svg)$";

        Pattern pa = Pattern.compile(allowPattern);
        Matcher ma = pa.matcher(fileName.toLowerCase());

        if(ma.matches()) { // 필터에 맞는 확장자를 가진 파일명이 들어왔을 경우
            System.out.println("update run");
            int pos = fileName.lastIndexOf(".");
            String extension = fileName.substring(pos+1);
            return extension;
        }
        else {
            return "error";
        }
    }


}
