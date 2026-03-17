package fitenessTrackerApp.service;

import fitenessTrackerApp.dto.workout.WorkoutCreateDto;
import fitenessTrackerApp.dto.workout.WorkoutResponseDTO;
import fitenessTrackerApp.dto.workout.WorkoutUpdateDto;
import fitenessTrackerApp.etities.UserEntity;
import fitenessTrackerApp.etities.Workout;
import fitenessTrackerApp.mappers.WorkoutMapper;
import fitenessTrackerApp.repository.UserRepo;
import fitenessTrackerApp.repository.WorkoutRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class WorkoutServiceImplementation implements WorkoutService {
    private final WorkoutRepo workoutRepo;
    private final WorkoutMapper workoutMapper;
    private final UserRepo userRepo;

    @Override
    public WorkoutResponseDTO createWorkout(String username, WorkoutCreateDto workoutCreateDto) {
        Workout workout = workoutMapper.fromWorkoutCreateDto(workoutCreateDto);
        UserEntity userEntity = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found with username " + username));
        workout.setUserEntity(userEntity);
        return workoutMapper.toWorkoutResponseDTO(workoutRepo.save(workout));
    }

    @Override
    public WorkoutResponseDTO getWorkoutById(long workoutId) {
        return workoutMapper.toWorkoutResponseDTO(workoutRepo.findById(workoutId).orElseThrow(() -> new RuntimeException("Workout not found with id " + workoutId)));
    }

    @Override
    public List<WorkoutResponseDTO> getWorkoutsByUser(String username) {

        return workoutMapper.toWorkoutResponseDTOList(workoutRepo.findAllByUserEntityUsername(username));
    }

    @Override
    public WorkoutResponseDTO updateWorkout(String username, long workoutId, WorkoutUpdateDto workoutUpdateDto) {
        Workout workout = workoutRepo.findById(workoutId).orElseThrow(() -> new RuntimeException("Workout not found with id " + workoutId));
        UserEntity userEntity = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found with username " + username));
        if (!username.equals(workout.getUserEntity().getUsername())) {
            throw new RuntimeException("Workout user username- " + workout.getUserEntity().getUsername() + " and account username- " + username + " is not matching");
        }
        if (workoutUpdateDto.getStart() != null) workout.setStart(workoutUpdateDto.getStart());
        if (workoutUpdateDto.getFinish() != null) workout.setFinish(workoutUpdateDto.getFinish());

        return workoutMapper.toWorkoutResponseDTO(workoutRepo.save(workout));
    }

    @Override
    public void deleteWorkout(long workoutId, String username) {
        Workout workout = workoutRepo.findById(workoutId).orElseThrow(() -> new RuntimeException("Workout not found with id " + workoutId));
        UserEntity userEntity = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found with username " + username));
        if (!username.equals(workout.getUserEntity().getUsername())) {
            throw new RuntimeException("Workout user username- " + workout.getUserEntity().getUsername() + " and account username- " + username + " is not matching");
        }
        workoutRepo.deleteById(workoutId);
    }
}
