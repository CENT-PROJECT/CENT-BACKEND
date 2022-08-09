package goingmerry.cent.ararm;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class AlarmService {

    private final AlarmRepository alarmRepository;

    /*
        findAllDesc 메소드의 트랜잭션 어노테이션(@Transactional)에 옵션이 하나 추가되었습니다.
        옵션(readOnly = true)을 주면 트랜잭션 범위는 유지하되, 조회 기능만 남겨두어 조회 속도가 개선됩니다.
     */
    // 사용자의 이메일(ToEmail), flag 를 통해 해당 사용자의 알람들 가져온다.
    @Transactional(readOnly = true)
    public List<getAlarmDto> findAllByToEmailAndValue(String toEmail, String value) {
        return alarmRepository.findAllByToEmailAndValue(toEmail, value)
                .map(getAlarmDto::new)
                .collect(Collectors.toList());
    }

    public saveAlarmDto createAlarm(saveAlarmDto dto) {

        log.info("################################################");

        saveAlarmDto response = new saveAlarmDto();

        Alarm entity = toEntity(dto);
        alarmRepository.save(entity);

        response.setToEmail(entity.getToEmail());
        response.setValue(entity.getValue());
        response.setFromEmail(entity.getFromEmail());
        response.setTeamName(entity.getTeamName());

        return response;
    }

    public Long delete(Long id) {

        Optional<Alarm> delAlarm = alarmRepository.findById(id);
        if(delAlarm.isPresent()) {
            Alarm alarm = delAlarm.get();
        } else {
            throw new NullPointerException();
        }
        alarmRepository.deleteById(delAlarm.get().getId());

        return delAlarm.get().getId();
    }



    public Alarm toEntity(saveAlarmDto saveDto) {
        return Alarm.builder()
                .fromEmail(saveDto.getFromEmail())
                .toEmail(saveDto.getToEmail())
                .teamName(saveDto.getTeamName())
                .value(saveDto.getValue())
                .build();
    }

}
