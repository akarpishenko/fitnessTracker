package fitenessTrackerApp.service;

import fitenessTrackerApp.dto.run.RunCreateDto;
import fitenessTrackerApp.dto.run.RunResponseDTO;
import fitenessTrackerApp.dto.run.RunUpdateDto;

import java.util.List;

public interface RunService {
    RunResponseDTO createRun(String username, RunCreateDto runCreateDto);

    RunResponseDTO getRunById(long id);

    List<RunResponseDTO> getRunsByUser(String username);

    void deleteRun(long runId, String username);

    RunResponseDTO updateRun(String username, long runId, RunUpdateDto runUpdateDto);
}
