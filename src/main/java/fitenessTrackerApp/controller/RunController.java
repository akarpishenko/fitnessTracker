package fitenessTrackerApp.controller;


import fitenessTrackerApp.dto.run.RunCreateDto;
import fitenessTrackerApp.dto.run.RunResponseDTO;
import fitenessTrackerApp.dto.run.RunUpdateDto;
import fitenessTrackerApp.service.RunService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/runs")
@RequiredArgsConstructor
@Validated
public class RunController {
    private final RunService runService;

    @PostMapping
    public RunResponseDTO save(@PathVariable long userId, @Valid @RequestBody RunCreateDto runCreateDto) {
        return runService.createRun(userId, runCreateDto);
    }

    @GetMapping("/{id}")
    public RunResponseDTO getById(@PathVariable long id, @PathVariable long userId) {
        return runService.getRunById(id);
    }

    @GetMapping
    public List<RunResponseDTO> getMyRuns(@PathVariable long userId) {
        return runService.getRunsByUser(userId);
    }

    @PatchMapping("/{id}")
    public RunResponseDTO updateById(@PathVariable long userId, @PathVariable long id, @Valid @RequestBody RunUpdateDto runUpdateDto) {
        return runService.updateRun(userId, id, runUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long userId, @PathVariable long id) {
        runService.deleteRun(userId, id);
    }
}
