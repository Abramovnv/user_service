package school.faang.user_service.filter.subscriptionFilter;

import org.springframework.stereotype.Component;
import school.faang.user_service.dto.SubscriptionFilterDto;
import school.faang.user_service.entity.User;

import java.util.stream.Stream;

@Component
public class SubscriptionEmailFilter implements SubscriptionFilter {
    @Override
    public boolean isApplicable(SubscriptionFilterDto subscriptionFilterDto) {
        return subscriptionFilterDto.getEmailPattern() != null;
    }

    @Override
    public Stream<User> apply(Stream<User> users, SubscriptionFilterDto subscriptionFilterDto) {
        return users.filter(user -> user.getEmail().equals(subscriptionFilterDto.getEmailPattern()));
    }
}
