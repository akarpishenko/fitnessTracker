package fitenessTrackerApp.mappers;

import fitenessTrackerApp.dto.user.UserCreateDto;
import fitenessTrackerApp.dto.user.UserResponseDTO;
import fitenessTrackerApp.etities.User;
import org.mapstruct.Mapper;

@Mapper(uses = {RunMapper.class, WorkoutMapper.class})
public interface UserMapper {
    User fromUserCreateDto(UserCreateDto userCreateDto);

    UserResponseDTO toUserResponse(User user);
}
