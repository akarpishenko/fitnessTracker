package fitenessTrackerApp.mappers;

import fitenessTrackerApp.dto.user.UserCreateDto;
import fitenessTrackerApp.dto.user.UserResponseDTO;
import fitenessTrackerApp.etities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RunMapper.class, WorkoutMapper.class})
public interface UserMapper {
    UserEntity fromUserCreateDto(UserCreateDto userCreateDto);

    @Mapping(source = "email", target = "email")
    @Mapping(source = "id", target = "id")
    UserResponseDTO toUserResponse(UserEntity userEntity);

    List<UserResponseDTO> toUserResponseList(List<UserEntity> userEntities);
}
