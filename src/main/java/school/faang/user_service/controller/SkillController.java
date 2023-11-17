package school.faang.user_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.SkillDto;
import school.faang.user_service.entity.Skill;
import school.faang.user_service.mapper.SkillMapper;
import school.faang.user_service.service.SkillService;
import school.faang.user_service.validator.SkillValidator;

@Controller
@RequestMapping("/api/v1/skill")
@RequiredArgsConstructor
@Slf4j
public class SkillController {
    private final SkillService skillService;
    private final SkillValidator skillValidator;
    private final SkillMapper skillMapper;
    private final UserContext userContext;

    @PostMapping("/create")
    public SkillDto create(@RequestBody SkillDto skillDto) {
        skillValidator.validateSkillDto(skillDto);

        Skill result = skillService.create(skillDto);
        return skillMapper.toDto(result);
    }


}
