package school.faang.user_service.validator;

import org.springframework.stereotype.Component;
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.exception.DataValidationException;

@Component
public class SkillValidator {
    public void validateSkillDto(SkillDto skillDto) {
        if (skillDto.getTitle().isBlank() || skillDto.getTitle().isEmpty()) {
            throw new DataValidationException("Skill title can't be empty");
        }
    }
}
