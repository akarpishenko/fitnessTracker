package fitenessTrackerApp.dto.user;

import lombok.Data;

@Data
public class UserUpdateDto {
    private String name;
    private String lastName;
    private String email;
    private String username;
}
