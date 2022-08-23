package goingmerry.cent.ararm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/alarm")
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping(value = "/get")
    public ResponseEntity<List<getAlarmDto>> getUserAlarm(@RequestParam String userEmail, String flagValue) {
        log.info("[API CALL : /api/alarm/get, request user is \"{}\" ]", userEmail);

        List<getAlarmDto> responseList = alarmService.findAllByToEmailAndValue(userEmail, flagValue);

    return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    // fromEmail OR teamName && toEmail, default value is W(wait)
    @PostMapping(value = "/create")
    public ResponseEntity<saveAlarmDto> createAlarm(@RequestBody getAlarmDto dto) {

//    public ResponseEntity createAlarm(@RequestBody Map<String, Object> dto) {

        log.info("[API CALL : /api/alarm/create," +
                " fromUser : {}, teamName : {}, toUser : {}]", dto.getFromEmail(), dto.getTeamName(), dto.getToEmail());

        String fromEmail = dto.getFromEmail();
        String teamName = dto.getTeamName();
        String toEmail = dto.getToEmail();

        log.info(dto.toString());

        saveAlarmDto saveDto = new saveAlarmDto();

        saveDto.setFromEmail(fromEmail);
        saveDto.setTeamName(teamName);
        saveDto.setToEmail(toEmail);
        // default value is W
        saveDto.setValue("W");

        saveAlarmDto response = alarmService.createAlarm(saveDto);

        log.info("User {} is create Alarm, status is {}", response.getToEmail(), response.getValue());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @DeleteMapping(value = "/delete")
//    public ResponseEntity<Long> deleteAlarm(@RequestBody AlarmDto dto) {
//        log.info("[API CALL : /api/alarm/delete," +
//                " fromUser : {}, teamName : {}, toUser : {}]", dto.getFromEmail(), dto.getTeamName(), dto.getToEmail());
//        Long id = alarmService.delete(dto.getId());
//
//        return new ResponseEntity<>(id, HttpStatus.OK);
//    }
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Long> deleteAlarm(@RequestParam Long id) {
        log.info("[API CALL : /api/alarm/delete, alarmId={}");
        Long deleteId = alarmService.delete(id);

        return new ResponseEntity<>(deleteId, HttpStatus.OK);
    }

}
