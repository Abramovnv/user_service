package school.faang.user_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.SkillCandidateDto;
import school.faang.user_service.dto.SkillDto;
import school.faang.user_service.service.SkillService;
import school.faang.user_service.validator.SkillValidator;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skill")
@RequiredArgsConstructor
@Slf4j
public class SkillController {
    private final SkillService skillService;
    private final SkillValidator skillValidator;
    private final UserContext userContext;

    @PostMapping("/create")
    public SkillDto create(@RequestBody @Valid SkillDto skillDto) {
        skillValidator.validateSkillDto(skillDto);
        return skillService.create(skillDto);
    }

    @GetMapping("/getOfferedSkills")
    public List<SkillCandidateDto> getOfferedSkills() {
        return skillService.getOfferedSkills(userContext.getUserId());
    }

    @GetMapping("/getUserSkills")
    public List<SkillDto> getUserSkills() {
        return skillService.getUserSkills(userContext.getUserId());
    }

    @GetMapping("/acquireSkillFromOffers")
    public SkillDto acquireSkillFromOffers(@RequestParam long skillId) {
        return skillService.acquireSkillFromOffers(skillId, userContext.getUserId());
    }

}