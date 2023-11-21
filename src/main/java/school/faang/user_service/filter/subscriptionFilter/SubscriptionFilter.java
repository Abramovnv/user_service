package school.faang.user_service.filter.subscriptionFilter;

import school.faang.user_service.dto.SubscriptionFilterDto;
import school.faang.user_service.entity.User;

import java.util.stream.Stream;

public interface SubscriptionFilter {
    boolean isApplicable(SubscriptionFilterDto subscriptionFilterDto);

    Stream<User> apply(Stream<User> users, SubscriptionFilterDto subscriptionFilterDto);
}
