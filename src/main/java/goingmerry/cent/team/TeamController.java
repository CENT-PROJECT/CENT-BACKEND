package goingmerry.cent.team;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/team")
public class TeamController {

    private final TeamService teamService;

    private final TeamRepository teamRepository;

    @RequestMapping(value = "/exist", method = RequestMethod.GET)
    public Map<String, String> existTeamName(@RequestBody Map<String, String> teamInfo) {

        log.info("[API CALL] : /api/team/exist");

        Map<String, String> res = new HashMap<>();

        String teamName = teamInfo.get("teamName");

        //errMsg같은 경우는 0,1의 코드 형태로 들어갈 수 있게 할 것.
        //DB에서 팀명으로 검색, 있는 팀명이라면 등록이 안 되게 하였다.
        if (teamService.isExistTeam(teamName)){
            res.put("ErrorMsg", "이미 존재하는 팀입니다. 다른 팀명을 사용하세요.");
            res.put("현재 팀명", teamName);
            res.put("code", "100");
            log.info("Response : {}",res);
        }
        else {
            res.put("ApprovalMsg", "사용 가능한 팀명입니다.");
        }
        return res;
    }

    //update api는 따로 생성 다시 할 것
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity createTeam(@RequestBody TeamSaveDto teamInfo) {

        log.info("[API CALL] : /api/team/create");

        Map<String, String> returnMap = new HashMap<>();
        log.info("{}", teamInfo);

        //팀의 이름 또는 활동지역은 필수값이기 때문에 들어가지 않은 채로 요청이 오면 디비 입력이 안되게 했다.
        //사실 프론트에서 해줘서 굳이 필요 있는 로직은 아니긴 하다.
        boolean isTeamNameNull = teamService.isRequireValueNull(teamInfo).get("teamName");
        log.error("isTeamNameNull? : {}", isTeamNameNull);
        boolean isAreaNull = teamService.isRequireValueNull(teamInfo).get("area");
        log.error("isAreaNull? : {}", isAreaNull);
        if (isTeamNameNull || isAreaNull){
            // Exception 처리 해줘야
            if (isTeamNameNull) {
                returnMap.put("TeamName Is Null!!", "팀명은 필수 값 입니다.");

            }
            if (isAreaNull) {
                returnMap.put("Area Is Null!!", "활동 지역은 필수 값 입니다.");
            }

            return new ResponseEntity<>(returnMap, HttpStatus.BAD_REQUEST);
        }
        // 필수 값을 모두 넣은 요청에 대해서 등록 또는 업데이트 로직 실행. -> 팀명이 있는 경우 검사하기 때문에 update api 따로 둬야 할 경우 생각중.
        // 로그인한 사용자만 팀생성 할 수 있도록 role 접근 로직 추가하면 exist 자체를 검사 안 하도록 조절할 수 있을 듯.
        // 프론트에서 한번 걸러서 오지만 api에 직접 접근하는 경우 있을 수 있기 때문에 일단 existTeamName 로직은 추가해둔 상태.
        else {

            // 22.7.25 추가, 팀장은 팀을 하나밖에 만들지 못 한다.
            if(teamService.isLeaderExist(teamInfo.getLeaderId())) {
                returnMap.put("ErrorMsg", "leader exist, Dont save!");
                return new ResponseEntity<>(returnMap, HttpStatus.BAD_REQUEST);
            }

            //DB에서 팀명으로 검색, 있는 팀명이라면 등록이 안 되게 하였다.
            if (!teamService.isExistTeam(teamInfo.getTeamName())){

                log.info("start save this team : " + teamInfo.getTeamName());
                TeamDto res = teamService.create(teamInfo);
                log.info("Response : {}", returnMap);

                return new ResponseEntity<>(res, HttpStatus.OK);
            }
            else {
                returnMap.put("ErrorMsg", "its exist TeamName, Dont save!");
                return new ResponseEntity<>(returnMap, HttpStatus.BAD_REQUEST);
            }
        }

    }

    // 팀 정보 업데이트(소개글, 이미지)
    // 이미지 formData 처리는?
    @PatchMapping(value = "/update")
    public ResponseEntity updateTeam(@RequestBody TeamDto req) {

        log.info("[API CALL] : /api/team/update");

        TeamDto res = teamService.update(req);

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity deleteTeam(@RequestParam Long teamId) {

        log.info("[API CALL] : /api/team/delete");

        var res = new HashMap<String, String>();

        try {
            teamService.deleteTeam(teamId);
            res.put("teamId", teamId.toString());

        }catch (Exception e) {
            log.error(e.getMessage());
            res.put("ErrorMsg", e.getMessage());
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    //팀의 다른 정보는 제하고, 팀의 이름만 리스트업
    @GetMapping(value = "/list/allTeamName")
    public List<String> listAllTeam() {

        log.info("[API CALL] : /api/team/list/allTeamName");

        return teamRepository.findTeamName();
    }

    @GetMapping(value = "/get/{teamId}")
    public ResponseEntity teamInfo(@PathVariable Long teamId) {

        log.info("[API CALL] : /api/team/get/{}",teamId);

        TeamDto res = teamService.teamInfo(teamId);

        if(res == null) {
            var errorRes = new HashMap<String, Object>();

            errorRes.put("code", 101);
            errorRes.put("msg", "존재하지 않는 팀입니다.");
            return new ResponseEntity<>(errorRes,HttpStatus.BAD_REQUEST);
        } else {

            return new ResponseEntity<>(res,HttpStatus.OK);
        }


    }

//  포메이션 관련

    @GetMapping(value = "/formation/get")
    public ResponseEntity getFormation(@RequestParam Long teamId) {

        log.info("[API CALL] : /api/team/formation/get");

        TeamFormationDto res = teamService.getFormation(teamId);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PatchMapping(value = "/formation/update")
    public ResponseEntity<TeamFormationDto> updateFormation(@RequestBody TeamFormationDto req) {

        log.info("[API CALL : /api/formation/formation/update");

        log.info(req.getFormationName());
        log.info(req.getTeamId().toString());
        for(int i=0;i<req.getPlayerList().size();i++) {
            log.info("{}", req.getPlayerList().get(i));
        }


        TeamFormationDto res = teamService.updateFormation(req);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    // 로고 이미지 관련

    @PostMapping(value = "/logo/update")
    public ResponseEntity updateLogo(@RequestParam("logo") MultipartFile logo, @RequestParam Long teamId) throws IOException {

        String fileName = StringUtils.cleanPath(logo.getOriginalFilename());

        // 확장자 추출 및 검사
        String extension = teamService.getExtension(fileName);

        if(extension.equals("error")) {

            Map<String, Object> res = new HashMap<>();
//            res.put("code", 401);
            res.put("msg", "지원하지 않는 확장자입니다.");

            return new ResponseEntity(res, HttpStatus.BAD_REQUEST);
        }

          TeamDto resDto = teamService.updateLogo(logo, teamId, extension);

        return new ResponseEntity<>(resDto, HttpStatus.OK);
    }

    @GetMapping(value = "/logo/get")
    public ResponseEntity getLogo(@RequestParam Long teamId) {

        TeamLogoDto logoDto = teamService.getLogo(teamId);
        Resource resource = logoDto.getResource();

//        if(!resource.exists()) {
        if(resource == null) {
            return new ResponseEntity<Resource>(HttpStatus.BAD_REQUEST);
        }

        HttpHeaders header = new HttpHeaders();

        Path filePath = null;
        try{
            filePath = Paths.get(logoDto.getFilePath());
            header.add("Content-type", Files.probeContentType(filePath));
        }catch(IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity(resource, header, HttpStatus.OK);

    }



//    ################################ 지역 관련 ################################
//
//
//    //지역을 도 / 시 값으로 전체 출력하는 api, 현재 json으로 list in map의 형식을 띄고 있다.
//    @RequestMapping(value = "/allArea", method = RequestMethod.GET)
//    public List<Map<String, String>> listAllCity() {
//
////        List<Map<String, String>> returnMap = areaRepository.findAllArea();
////
////        return returnMap;
//        return areaRepository.findAllArea();
//    }
//
//    //도명을 받아서 시를 추출해내는 api
//    @RequestMapping(value = "/getcity", method = RequestMethod.GET)
//    public List<String> listCityByRegion(@RequestParam Map<String, String> regionName) {
//
//        String region = regionName.get("region");
//
//        List<String> regionList = areaRepository.findCityByRegion(region);
//        log.debug("{}", regionList);
//        return regionList;
//    }
//
//    //지역(city)를 받아서 해당하는 팀명 리스트 보내주는 api
//    @RequestMapping(value = "/list/cityteam", method = RequestMethod.GET)
//    public List<String> listTeamNameByCity(@RequestParam Map<String, String> area) {
//
//        String city = area.get("city");
////        List<String> returnList = teamRepository.findTeamNameByArea(city);
////
////        return returnList;
//        return teamRepository.findTeamNameByArea(city);
//    }

}
