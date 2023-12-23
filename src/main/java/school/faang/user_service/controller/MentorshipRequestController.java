package school.faang.user_service.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.mentorship.MentorshipRequestDto;
import school.faang.user_service.dto.mentorship.RejectionReasonDto;
import school.faang.user_service.service.MentorshipRequestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mentorship")
@Slf4j
public class MentorshipRequestController {
    private final MentorshipRequestService mentorshipRequestService;
    private final UserContext userContext;

    @PostMapping("/request")
    public MentorshipRequestDto requestMentorship(@RequestBody @Valid MentorshipRequestDto mentorshipRequestDto) {
        return mentorshipRequestService.requestMentorship(mentorshipRequestDto);
    }

    @GetMapping("/getRequests")
    public List<MentorshipRequestDto> getRequests() {
        return mentorshipRequestService.getRequests(userContext.getUserId());
    }

    @PostMapping("/acceptRequest/{requestId}")
    public MentorshipRequestDto acceptRequest(@PathVariable @Min(0) long requestId) {
        return mentorshipRequestService.acceptRequest(requestId);
    }

    @PostMapping("/rejectRequest/{requestId}")
    public MentorshipRequestDto rejectRequest(@PathVariable long requestId, @RequestParam @Valid RejectionReasonDto rejectionReasonDto){
        return mentorshipRequestService.rejectRequest(requestId,rejectionReasonDto);
    }
}
