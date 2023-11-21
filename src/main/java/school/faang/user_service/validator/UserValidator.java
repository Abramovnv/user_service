package school.faang.user_service.validator;

import org.springframework.stereotype.Component;
import school.faang.user_service.exception.DataValidationException;

@Component
public class UserValidator {
    public void validateFollowerIdAndFolloweeId(long followerId, long followeeId) {
        if (followeeId == followerId) {
            throw new DataValidationException("You cannot use this action on yourself.");
        }
    }

}
