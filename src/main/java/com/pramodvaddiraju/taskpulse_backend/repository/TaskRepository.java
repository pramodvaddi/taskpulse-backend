package com.pramodvaddiraju.taskpulse_backend.repository;

import com.pramodvaddiraju.taskpulse_backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByDueTimeLessThanEqualAndStatus(
            LocalDateTime tile,
            String status
    );


}
