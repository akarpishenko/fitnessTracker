package fitenessTrackerApp.service;

import fitenessTrackerApp.dto.workout.WorkoutCreateDto;
import fitenessTrackerApp.dto.workout.WorkoutResponseDTO;
import fitenessTrackerApp.dto.workout.WorkoutUpdateDto;
import fitenessTrackerApp.etities.User;
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
    public WorkoutResponseDTO createWorkout(long userId, WorkoutCreateDto workoutCreateDto) {
        Workout workout = workoutMapper.fromWorkoutCreateDto(workoutCreateDto);
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        workout.setUser(user);
        return workoutMapper.toWorkoutResponseDTO(workoutRepo.save(workout));
    }

    @Override
    public WorkoutResponseDTO getWorkoutById(long workoutId) {
        return workoutMapper.toWorkoutResponseDTO(workoutRepo.findById(workoutId).orElseThrow(() -> new RuntimeException("Workout not found with id " + workoutId)));
    }

    @Override
    public List<WorkoutResponseDTO> getWorkoutsByUser(long userId) {

        return workoutMapper.toWorkoutResponseDTOList(workoutRepo.findAllByUserId(userId));
    }

    @Override
    public WorkoutResponseDTO updateWorkout(long userId, long workoutId, WorkoutUpdateDto workoutUpdateDto) {
        Workout workout = workoutRepo.findById(workoutId).orElseThrow(() -> new RuntimeException("Workout not found with id " + workoutId));
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        if (workout.getUser().getId() != userId) {
            throw new RuntimeException("Workout user id- " + workout.getUser().getId() + " and account id- " + userId + " is not matching");
        }
        if (workoutUpdateDto.getStart() != null) workout.setStart(workoutUpdateDto.getStart());
        if (workoutUpdateDto.getFinish() != null) workout.setFinish(workoutUpdateDto.getFinish());

        return workoutMapper.toWorkoutResponseDTO(workoutRepo.save(workout));
    }

    @Override
    public void deleteWorkout(long workoutId, long userId) {
        Workout workout = workoutRepo.findById(workoutId).orElseThrow(() -> new RuntimeException("Workout not found with id " + workoutId));
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        if (workout.getUser().getId() != userId) {
            throw new RuntimeException("Workout user id- " + workout.getUser().getId() + " and account id- " + userId + " is not matching");
        }
        workoutRepo.deleteById(workoutId);
    }
}
