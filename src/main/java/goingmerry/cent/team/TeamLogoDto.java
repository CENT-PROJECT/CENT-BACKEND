package goingmerry.cent.team;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.Resource;

@Getter
@Setter
@NoArgsConstructor
public class TeamLogoDto {

    private Resource resource;

    private String filePath;


}
