package fitenessTrackerApp.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateDto {
    @NotBlank
    public String name;
    @NotBlank
    public String lastName;
    @NotBlank
    @Email
    public String email;
    @NotBlank
    public String username;
}
