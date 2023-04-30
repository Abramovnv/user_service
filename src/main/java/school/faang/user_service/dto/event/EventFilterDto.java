package school.faang.user_service.dto.event;

import lombok.Data;
import school.faang.user_service.entity.Skill;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventFilterDto {
    private String titlePattern;
    private LocalDateTime since;
    private LocalDateTime till;
    private String ownerPattern;
    private long ownerIdPattern;
    private String locationPattern;
    private String skillsPattern;
    private List<Skill> skills;
    private String type;
    private String status;
    private int page;
    private int pageSize;
}