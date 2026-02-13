package com.pramodvaddiraju.taskpulse_backend.service;

import com.pramodvaddiraju.taskpulse_backend.dto.TaskRequestDto;
import com.pramodvaddiraju.taskpulse_backend.dto.TaskResponseDto;
import com.pramodvaddiraju.taskpulse_backend.entity.Task;
import com.pramodvaddiraju.taskpulse_backend.exception.ResourceNotFoundException;
import com.pramodvaddiraju.taskpulse_backend.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{

    private static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);
    private ModelMapper modelMapper;
    private TaskRepository taskRepository;

    public TaskServiceImpl(ModelMapper modelMapper, TaskRepository taskRepository){
        this.modelMapper = modelMapper;
        this.taskRepository = taskRepository;
    }



    @Override
    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {
        Task task = modelMapper.map(taskRequestDto, Task.class);
        task.setStatus("PENDING");
        log.debug("Creating task for the user with email: {}", task.getEmail());
        Task createdTask = taskRepository.save(task);
        log.info("Task created with email: {}", task.getEmail());
        return modelMapper.map(createdTask, TaskResponseDto.class);
    }

    @Override
    public Page<TaskResponseDto> getAllTasks(Pageable pageable) {
       Page<Task> taskPage = taskRepository.findAll(pageable);

        return taskPage.map(task -> modelMapper.map(task, TaskResponseDto.class));
    }

    @Override
    public TaskResponseDto getTaskById(Long id) {
        Task getTaskById = taskRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("No content found with id: " + id)
        );
        return modelMapper.map(getTaskById, TaskResponseDto.class);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found with id: " + id)
        );

        taskRepository.delete(task);
    }

    @Override
    public TaskResponseDto markTaskAsCompleted(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Not found with id: " +id)
        );

        task.setStatus("COMPLETED");

        Task updatedTask = taskRepository.save(task);
        log.info("Task marked as completed with id: {}", task.getId());

        return modelMapper.map(updatedTask, TaskResponseDto.class);
    }
}
