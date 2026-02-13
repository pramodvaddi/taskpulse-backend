package com.pramodvaddiraju.taskpulse_backend.service;

import com.pramodvaddiraju.taskpulse_backend.dto.TaskRequestDto;
import com.pramodvaddiraju.taskpulse_backend.dto.TaskResponseDto;
import com.pramodvaddiraju.taskpulse_backend.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    TaskResponseDto createTask(TaskRequestDto taskRequestDto);
    Page<TaskResponseDto> getAllTasks(Pageable pageable);
    TaskResponseDto getTaskById(Long id);
    void deleteTask(Long id);
    TaskResponseDto markTaskAsCompleted(Long id);
}
