package school.faang.user_service.filter.subscriptionFilter;

import org.springframework.stereotype.Component;
import school.faang.user_service.dto.SubscriptionFilterDto;
import school.faang.user_service.entity.User;

import java.util.stream.Stream;

@Component
public class SubscriptionCityFilter implements SubscriptionFilter {
    @Override
    public boolean isApplicable(SubscriptionFilterDto subscriptionFilterDto) {
        return subscriptionFilterDto.getCityPattern() != null;
    }

    @Override
    public Stream<User> apply(Stream<User> users, SubscriptionFilterDto subscriptionFilterDto) {
        return users.filter(user -> user.getCity().equals(subscriptionFilterDto.getCityPattern()));
    }
}
