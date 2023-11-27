package school.faang.user_service.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.faang.user_service.dto.SkillCandidateDto;
import school.faang.user_service.dto.SkillDto;
import school.faang.user_service.entity.Skill;
import school.faang.user_service.entity.UserSkillGuarantee;
import school.faang.user_service.entity.recommendation.SkillOffer;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.mapper.SkillMapper;
import school.faang.user_service.repository.SkillRepository;
import school.faang.user_service.repository.UserSkillGuaranteeRepository;
import school.faang.user_service.repository.recommendation.SkillOfferRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;
    private final SkillOfferRepository skillOfferRepository;
    private final UserSkillGuaranteeRepository userSkillGuaranteeRepository;
    private final SkillMapper skillMapper;

    @Value("${spring.data.min_skill_offers}")
    private static long MIN_SKILL_OFFERS;

    @Transactional
    public SkillDto create(SkillDto skillDto) {
        if (skillRepository.existsByTitle(skillDto.getTitle())) {
            throw new DataValidationException("Skill with title: " + skillDto.getTitle() + " already exist");
        }
        return skillMapper.toDto(skillRepository.save(skillMapper.toEntity(skillDto)));
    }

    @Transactional(readOnly = true)
    public List<SkillCandidateDto> getOfferedSkills(long userId) {
        List<Skill> offeredSkills = skillRepository.findSkillsOfferedToUser(userId);
        return offeredSkills.stream()
                .collect(Collectors.groupingBy(Skill::getTitle))
                .values().stream()
                .map(skills -> skillMapper.toSkillCandidateDto(skillMapper.toDto(skills.get(0)), skills.size())).toList();
    }

    @Transactional(readOnly = true)
    public List<SkillDto> getUserSkills(long userId) {
        return skillRepository.findAllByUserId(userId).stream()
                .map(skillMapper::toDto).toList();
    }

    @Transactional
    public SkillDto acquireSkillFromOffers(long skillId, long userId) {
        Skill skill = skillRepository.findSkillById(userId);
        if (skillRepository.findUserSkill(skillId, userId).isPresent()) {
            throw new DataValidationException("User already have skill "
                    + skill.getTitle());
        }
        List<SkillOffer> skillOffers = skillOfferRepository.findAllOffersOfSkill(skillId, userId);
        if (skillOffers.size() < 3) {
            throw new DataValidationException("You have less then 3 offers");
        }

        skillRepository.assignSkillToUser(skillId, userId);
        skillOffers.forEach(skillOffer -> saveUserSkillGuarantee(skillOffer, skill));
        return skillMapper.toDto(skillOffers.get(0).skill);
    }

    private void saveUserSkillGuarantee(SkillOffer skillOffer, Skill skill) {
        userSkillGuaranteeRepository.save(UserSkillGuarantee.builder()
                .user(skillOffer.getRecommendation().getReceiver())
                .skill(skill)
                .guarantor(skillOffer.getRecommendation().getAuthor())
                .build());
    }
}
