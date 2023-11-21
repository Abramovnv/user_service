package school.faang.user_service.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.service.SubscriptionService;

@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
@Slf4j
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/followUser")
    public void followUser(@RequestParam @Min(0) @NotBlank long followerId,
                           @RequestParam @Min(0) @NotBlank long followeeId) {
        if (followerId == followeeId) {
            log.error("Uses bad id" + followerId + " or" + followeeId);
            throw new DataValidationException("This feature cannot be applied to this account.");
        }
        subscriptionService.followUser(followerId, followeeId);
    }
}
