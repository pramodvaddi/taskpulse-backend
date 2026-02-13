package com.pramodvaddiraju.taskpulse_backend.service;

import com.pramodvaddiraju.taskpulse_backend.entity.Task;
import com.pramodvaddiraju.taskpulse_backend.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class TaskReminderScheduler {

    private static final Logger log =
            LoggerFactory.getLogger(TaskReminderScheduler.class);

    private final TaskRepository taskRepository;
    private final EmailService emailService;

    public TaskReminderScheduler(TaskRepository taskRepository,
                                 EmailService emailService) {
        this.taskRepository = taskRepository;
        this.emailService = emailService;
    }

    // Runs every 60 seconds
    @Scheduled(fixedRate = 60000)
    public void processDueTasks() {

        List<Task> dueTasks =
                taskRepository
                        .findByDueTimeLessThanEqualAndStatus(
                                LocalDateTime.now(),
                                "PENDING"
                        );

        for (Task task : dueTasks) {

            emailService.sendEmail(
                    task.getEmail(),
                    "Task Reminder",
                    "Your task \"" + task.getTitle() + "\" is due."
            );

            task.setStatus("REMINDED");
            taskRepository.save(task);

            log.info("Reminder sent for task id: {}", task.getId());
        }
    }
}
