package school.faang.user_service.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import school.faang.user_service.dto.mentorship.MentorshipRequestDto;
import school.faang.user_service.entity.MentorshipRequest;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
@DecoratedWith(MentorshipRequestMapperDecorator.class)
public interface MentorshipRequestMapper {
    //в учебных целях использовал декоратор, чтобы добавить бин userRepository, для маппинга сущности user из id
    MentorshipRequest toEntity(MentorshipRequestDto mentorshipRequestDto);

    @Mapping(source = "requester.id", target = "requesterId")
    @Mapping(source = "receiver.id", target = "receiverId")
    MentorshipRequestDto toDto(MentorshipRequest mentorshipRequest);

    List<MentorshipRequestDto> toDto(List<MentorshipRequest> mentorshipRequestList);

}
