package school.faang.user_service.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import school.faang.user_service.dto.mentorship.MentorshipRequestDto;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.repository.UserRepository;
import school.faang.user_service.repository.mentorship.MentorshipRequestRepository;

import java.time.LocalDate;
import java.time.Period;

@Component
@RequiredArgsConstructor
public class MentorshipRequestValidator {
    private final UserRepository userRepository;
    private final MentorshipRequestRepository mentorshipRequestRepository;

    public void validateOnCreation(MentorshipRequestDto mentorshipRequestDto) {
        if (mentorshipRequestDto.getRequesterId() == mentorshipRequestDto.getReceiverId()) {
            throw new DataValidationException("RequesterId and ReceiverId must be different");
        }
        userRepository.findById(mentorshipRequestDto.getRequesterId())
                .orElseThrow(() -> new DataValidationException("Requester is not found"));
        userRepository.findById(mentorshipRequestDto.getRequesterId())
                .orElseThrow(() -> new DataValidationException("Requester is not found"));

        mentorshipRequestRepository.findLatestRequest(mentorshipRequestDto.getRequesterId(),
                mentorshipRequestDto.getReceiverId()).ifPresent(request -> {
            if (Period.between(request.getCreatedAt().toLocalDate(), LocalDate.now()).getDays() < 90) {
                throw new RuntimeException("you can send a request no more than once every three months");
            }
        });
    }
}
