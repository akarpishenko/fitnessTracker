package fitenessTrackerApp.controller;


import fitenessTrackerApp.dto.run.RunCreateDto;
import fitenessTrackerApp.dto.run.RunResponseDTO;
import fitenessTrackerApp.dto.run.RunUpdateDto;
import fitenessTrackerApp.service.RunService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/runs")
@RequiredArgsConstructor
@Validated
public class RunController {
    private final RunService runService;

    @PostMapping
    public RunResponseDTO save(Authentication authentication, @Valid @RequestBody RunCreateDto runCreateDto) {
        return runService.createRun(authentication.getName(), runCreateDto);
    }

    @GetMapping("/{id}")
    public RunResponseDTO getById(@PathVariable long id) {
        return runService.getRunById(id);
    }

    @GetMapping
    public List<RunResponseDTO> getMyRuns(Authentication authentication) {
        return runService.getRunsByUser(authentication.getName());
    }

    @PatchMapping("/{id}")
    public RunResponseDTO updateById(Authentication authentication, @PathVariable long id, @Valid @RequestBody RunUpdateDto runUpdateDto) {
        return runService.updateRun(authentication.getName(), id, runUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(Authentication authentication, @PathVariable long id) {
        runService.deleteRun(id, authentication.getName());
    }
}
