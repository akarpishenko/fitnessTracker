package fitenessTrackerApp.mappers;

import fitenessTrackerApp.dto.run.RunCreateDto;
import fitenessTrackerApp.dto.run.RunResponseDTO;
import fitenessTrackerApp.etities.Run;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {UserMapper.class})
public interface RunMapper {
    Run fromRunCreateDto(RunCreateDto runCreateDto);

    @Mapping(target = "userId", source = "user.id")
    RunResponseDTO toRunResponseDTO(Run run);
}
