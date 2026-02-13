package com.pramodvaddiraju.taskpulse_backend.controller;

import com.pramodvaddiraju.taskpulse_backend.dto.TaskRequestDto;
import com.pramodvaddiraju.taskpulse_backend.dto.TaskResponseDto;
import com.pramodvaddiraju.taskpulse_backend.service.TaskService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);
    private TaskService taskService;
    private ModelMapper modelMapper;

    public TaskController(ModelMapper modelMapper, TaskService taskService){
        this.taskService = taskService;
        this.modelMapper = modelMapper;

    }
    @PostMapping
    ResponseEntity<TaskResponseDto> createTask(@Valid @RequestBody TaskRequestDto taskRequestDto){
        log.info("Task fetched successfully");
        return ResponseEntity.status(201).body(taskService.createTask(taskRequestDto));
    }
    @GetMapping
    ResponseEntity<Page<TaskResponseDto>> getAllTasks(Pageable pageable){
        log.info("Fetching all tasks");
        return ResponseEntity.ok().body(taskService.getAllTasks(pageable));
    }
    @GetMapping("/{id}")
    ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Long id){
        log.info("Fetching task by id " , id);
        return ResponseEntity.ok().body(taskService.getTaskById(id));
    }
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTask(@PathVariable Long id){
        log.info("Deleted task with id ", id);
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/complete")
    ResponseEntity<TaskResponseDto> markTaskCompleted(@PathVariable Long id){
        log.info("Task successfully marked as complete");
        return ResponseEntity.ok().body(taskService.markTaskAsCompleted(id));
    }
}
