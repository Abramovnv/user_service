package school.faang.user_service.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.dto.user.UserFilterDto;
import school.faang.user_service.service.SubscriptionService;
import school.faang.user_service.validator.UserValidator;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscription")
@RequiredArgsConstructor
@Slf4j
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final UserValidator userValidator;

    @PostMapping("/followUser")
    @ResponseStatus(HttpStatus.OK)
    public void followUser(@RequestParam @Min(0) @NotBlank long followerId,
                           @RequestParam @Min(0) @NotBlank long followeeId) {
        userValidator.validateFollowerIdAndFolloweeId(followerId, followeeId);
        subscriptionService.followUser(followerId, followeeId);
    }

    @PostMapping("/unfollowUser")
    @ResponseStatus(HttpStatus.OK)
    public void unfollowUser(@RequestParam @Min(0) long followerId,
                             @RequestParam @Min(0) long followeeId) {
        userValidator.validateFollowerIdAndFolloweeId(followerId, followeeId);
        subscriptionService.unfollowUser(followerId, followeeId);
    }

    @GetMapping("/getFollowers/{followee}")
    //name - это то, как мы назвали в url, обязательный параметр стоит по дефолту.
    public List<UserDto> getFollowers(@PathVariable(name = "followee", required = true) long followeeId,
                                      @RequestBody(required = false) UserFilterDto userFilterDto) {
        return subscriptionService.getFollowers(followeeId, userFilterDto);
    }

    @GetMapping("/getFolloweeCount")
    public int getFolloweeCount(@RequestParam long followeeId) {
        return subscriptionService.getFolloweeCount(followeeId);
    }

    @GetMapping("/getFollowing/{followerId}")
    public List<UserDto> getFollowing(@PathVariable long followerId, @RequestBody(required = false) UserFilterDto userFilterDto) {
        return subscriptionService.getFollowing(followerId, userFilterDto);
    }

    @GetMapping("/getFollowingCount")
    public int getFollowingCount(@RequestParam @Min(0) long followerId) {
        return subscriptionService.getFollowerCount(followerId);
    }
}
