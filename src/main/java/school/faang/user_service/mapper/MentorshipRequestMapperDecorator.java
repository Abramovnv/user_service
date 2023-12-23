package school.faang.user_service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import school.faang.user_service.dto.mentorship.MentorshipRequestDto;
import school.faang.user_service.entity.MentorshipRequest;
import school.faang.user_service.repository.UserRepository;


@Component
public abstract class MentorshipRequestMapperDecorator implements MentorshipRequestMapper {
    @Autowired
    private MentorshipRequestMapper mentorshipRequestMapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    public MentorshipRequest toEntity(MentorshipRequestDto mentorshipRequestDto) {
        MentorshipRequest result = mentorshipRequestMapper.toEntity(mentorshipRequestDto);
        result.setRequester(userRepository.findById(mentorshipRequestDto.getRequesterId()).orElseThrow(() -> new RuntimeException("User is not found")));
        result.setReceiver(userRepository.findById(mentorshipRequestDto.getReceiverId()).orElseThrow(() -> new RuntimeException("User is not found")));
        return result;
    }
}
