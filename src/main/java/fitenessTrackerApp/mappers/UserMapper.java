package fitenessTrackerApp.mappers;

import fitenessTrackerApp.dto.user.UserCreateDto;
import fitenessTrackerApp.dto.user.UserResponseDTO;
import fitenessTrackerApp.etities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RunMapper.class, WorkoutMapper.class})
public interface UserMapper {
    User fromUserCreateDto(UserCreateDto userCreateDto);

    @Mapping(source = "email", target = "email")
    @Mapping(source = "id", target = "id")
    UserResponseDTO toUserResponse(User user);

    List<UserResponseDTO> toUserResponseList(List<User> users);
}
