package school.faang.user_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.faang.user_service.dto.UserDto;
import school.faang.user_service.dto.UserFilterDto;
import school.faang.user_service.entity.User;
import school.faang.user_service.filter.userFilter.UserFilter;
import school.faang.user_service.mapper.UserMapper;
import school.faang.user_service.repository.SubscriptionRepository;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final List<UserFilter> userFilters;
    private final UserMapper userMapper;

    @Transactional
    public void followUser(long followerId, long followeeId) {
        if (subscriptionRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId)) {
            String userName = subscriptionRepository.findById(followerId)
                    .map(User::getUsername)
                    .orElse("this user");
            log.info(String.format("User with id: %d already subscribed to: %d ", followerId, followeeId));
            throw new RuntimeException("you are already subscribed to " + userName);
        }
        subscriptionRepository.followUser(followerId, followeeId);
    }

    @Transactional
    public void unfollowUser(long followerId, long followeeId) {
        subscriptionRepository.unfollowUser(followerId, followeeId);
    }

    //Такие фильтрации лучше делать на стороне базы, в учебных целях реализовал на стороне джавы
    @Transactional(readOnly = true)
    public List<UserDto> getFollowers(long followeeId, UserFilterDto userFilterDto) {
        if (userFilterDto != null) {
            Stream<User> users = subscriptionRepository.findByFolloweeId(followeeId);
            return userMapper.toDto(usersAfterFilter(users, userFilterDto));
        }
        return userMapper.toDto(subscriptionRepository.findByFolloweeId(followeeId).toList());
    }

    @Transactional(readOnly = true)
    public int getFolloweeCount(long followerId) {
        return subscriptionRepository.findFolloweesAmountByFollowerId(followerId);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getFollowing(long followerId, UserFilterDto userFilterDto) {
        if (userFilterDto != null) {
            Stream<User> users = subscriptionRepository.findByFollowerId(followerId);
            return userMapper.toDto(usersAfterFilter(users, userFilterDto));
        }
        return userMapper.toDto(subscriptionRepository.findByFolloweeId(followerId).toList());
    }

    private List<User> usersAfterFilter(Stream<User> users, UserFilterDto userFilterDto) {
        return userFilters.stream()
                .filter(filter -> filter.isApplicable(userFilterDto))
                .flatMap(userFilter -> userFilter.apply(users, userFilterDto))
                .toList();
    }

    public int getFollowerCount(long followerId) {
        return subscriptionRepository.findFolloweesAmountByFollowerId(followerId);
    }
}
