package fitenessTrackerApp.service;

import fitenessTrackerApp.dto.run.RunCreateDto;
import fitenessTrackerApp.dto.run.RunResponseDTO;
import fitenessTrackerApp.dto.run.RunUpdateDto;
import fitenessTrackerApp.etities.Run;
import fitenessTrackerApp.mappers.RunMapper;
import fitenessTrackerApp.repository.RunRepo;
import fitenessTrackerApp.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class RunServiceImplementation implements RunService {
    private static final int CALORIES_PER_KILOMETER = 62;
    private final RunRepo runRepo;
    private final UserRepo userRepo;
    private final RunMapper runMapper;

    @Override
    public RunResponseDTO createRun(long userId, RunCreateDto runCreateDto) {
        Run run = runMapper.fromRunCreateDto(runCreateDto);
        run.setUser(userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id " + userId)));
        run.setCaloriesBurned(calculateCalories(run));

        return runMapper.toRunResponseDTO(runRepo.save(run));
    }

    @Override
    public RunResponseDTO getRunById(long id) {

        return runMapper.toRunResponseDTO(runRepo.findById(id).orElseThrow(() -> new RuntimeException("Run not found with id " + id)));
    }

    @Override
    public List<RunResponseDTO> getRunsByUser(long userId) {
        return runMapper.toRunResponseDTOList(runRepo.findAllByUserId(userId));
    }

    @Override
    public void deleteRun(long runId, long userId) {
        Run run = runRepo.findById(runId).orElseThrow(() -> new RuntimeException("Run not found with id " + runId));
        if (userId != run.getUser().getId()) {
            throw new RuntimeException("Run user id- " + run.getUser().getId() + " and account id- " + userId + " is not matching ");
        }
        runRepo.deleteById(runId);
    }

    @Override
    public RunResponseDTO updateRun(long userId, long runId, RunUpdateDto runUpdateDto) {
        Run run = runRepo.findById(runId).orElseThrow(() -> new RuntimeException("Run not found with id " + runId));
        if (userId != run.getUser().getId()) {
            throw new RuntimeException("Run user id- " + run.getUser().getId() + " and account id- " + userId + " is not matching ");
        }
        if (runUpdateDto.getStart() != null) run.setStart(runUpdateDto.getStart());
        if (runUpdateDto.getFinish() != null) run.setFinish(runUpdateDto.getFinish());
        if (runUpdateDto.getDistanceKm() != null) run.setDistanceKm(runUpdateDto.getDistanceKm());
        if (runUpdateDto.getAveragePace() != null) run.setAveragePace(runUpdateDto.getAveragePace());
        run.setCaloriesBurned(calculateCalories(run));
        return runMapper.toRunResponseDTO(runRepo.save(run));
    }

    private double calculateCalories(Run run) {
        return run.getDistanceKm() * CALORIES_PER_KILOMETER;
    }
}
