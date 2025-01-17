package school.faang.user_service.filter.userFilter;

import school.faang.user_service.dto.user.UserFilterDto;
import school.faang.user_service.entity.User;

import java.util.stream.Stream;

public interface UserFilter {
    boolean isApplicable(UserFilterDto userFilterDto);

    Stream<User> apply(Stream<User> users, UserFilterDto userFilterDto);
}
