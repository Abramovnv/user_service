package school.faang.user_service.dto.mentorship;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MentorshipRequestDto {
    private long id;

    @NotBlank(message = "This field mustn't be empty")
    private String description;

    @Min(0)
    private long requesterId;

    @Min(0)
    private long receiverId;

    private String rejectionReason;

    private LocalDateTime createdAt;
}
