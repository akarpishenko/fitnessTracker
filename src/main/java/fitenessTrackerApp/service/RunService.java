package fitenessTrackerApp.service;

import fitenessTrackerApp.dto.run.RunCreateDto;
import fitenessTrackerApp.dto.run.RunResponseDTO;
import fitenessTrackerApp.dto.run.RunUpdateDto;

import java.util.List;

public interface RunService {
    RunResponseDTO createRun(long userId, RunCreateDto runCreateDto);

    RunResponseDTO getRunById(long id);

    List<RunResponseDTO> getRunsByUser(long userId);

    void deleteRun(long runId, long userId);

    RunResponseDTO updateRun(long userId, long runId, RunUpdateDto runUpdateDto);
}
