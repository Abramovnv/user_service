package school.faang.user_service.dto.mentorship;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RejectionReasonDto {
    @NotBlank(message = "reason field must be not empty")
    private String reason;

}
