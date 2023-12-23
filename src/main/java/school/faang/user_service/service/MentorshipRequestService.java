package school.faang.user_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.faang.user_service.dto.mentorship.MentorshipRequestDto;
import school.faang.user_service.dto.mentorship.RejectionReasonDto;
import school.faang.user_service.entity.MentorshipRequest;
import school.faang.user_service.entity.RequestStatus;
import school.faang.user_service.mapper.MentorshipRequestMapper;
import school.faang.user_service.repository.UserRepository;
import school.faang.user_service.repository.mentorship.MentorshipRequestRepository;
import school.faang.user_service.validator.MentorshipRequestValidator;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MentorshipRequestService {
    private final MentorshipRequestRepository mentorshipRequestRepository;
    private final MentorshipRequestMapper mentorshipRequestMapper;
    private final MentorshipRequestValidator mentorshipRequestValidator;
    private final UserRepository userRepository;


    @Transactional
    public MentorshipRequestDto requestMentorship(MentorshipRequestDto mentorshipRequestDto) {
        mentorshipRequestValidator.validateOnCreation(mentorshipRequestDto);
        MentorshipRequest mentorshipRequest = mentorshipRequestMapper.toEntity(mentorshipRequestDto);
        mentorshipRequest.setStatus(RequestStatus.PENDING);
        return mentorshipRequestMapper.toDto(mentorshipRequestRepository.save(mentorshipRequest));
    }

    @Transactional(readOnly = true)
    public List<MentorshipRequestDto> getRequests(long userId) {
        List<MentorshipRequest> mentorshipRequests = mentorshipRequestRepository
                .findMentorshipRequestByReceiver(userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User is not found")));
        return mentorshipRequestMapper.toDto(mentorshipRequests);
    }

    @Transactional
    public MentorshipRequestDto acceptRequest(long requestId) {
        Optional<MentorshipRequest> request = mentorshipRequestRepository.findById(requestId);
        if (request.isEmpty()) {
            throw new RuntimeException(String.format("Request with id:{0} is not found", requestId));
        }
        if (request.get().getRequester().getMentors().stream().anyMatch(user -> user.getId() == request.get().getReceiver().getId())) {
            throw new RuntimeException("This user is already your mentor");
        }
        request.get().getRequester().getMentors().add(request.get().getReceiver());
        request.get().setStatus(RequestStatus.ACCEPTED);
        return mentorshipRequestMapper.toDto(request.get());
    }

    @Transactional
    public MentorshipRequestDto rejectRequest(long requestId, RejectionReasonDto rejectionReasonDto) {
        Optional<MentorshipRequest> request = mentorshipRequestRepository.findById(requestId);
        if (request.isEmpty()) {
            throw new RuntimeException(String.format("Request with id:{0} is not found", requestId));
        }
        request.get().setStatus(RequestStatus.REJECTED);
        request.get().setRejectionReason(rejectionReasonDto.getReason());
        return mentorshipRequestMapper.toDto(request.get());
    }
}
