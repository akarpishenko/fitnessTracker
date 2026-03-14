package fitenessTrackerApp.dto.user;

import lombok.Data;

@Data
public class UserUpdateDto {
    public String name;
    public String lastName;
    public String email;
    public String username;
}
