package fitenessTrackerApp.repository;

import fitenessTrackerApp.etities.Run;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RunRepo extends JpaRepository<Run, Long> {
    List<Run> findAllByUserId(long id);
}
