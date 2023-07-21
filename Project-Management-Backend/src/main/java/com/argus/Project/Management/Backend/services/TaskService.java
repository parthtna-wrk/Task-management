package com.argus.Project.Management.Backend.services;

import com.argus.Project.Management.Backend.DTO.CountType;
import com.argus.Project.Management.Backend.model.Task;
import com.argus.Project.Management.Backend.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Transactional
    public List<Task> getTasks(){
        return taskRepository.getAllTaskDueDateDesc();
    }


    @Transactional
    public Task save(Task task) {
        return taskRepository.saveAndFlush(task);
    }

    @Transactional
    public boolean existsById(Long id){
        return taskRepository.existsById(id);

    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);

    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    public List<CountType> getPercentageGroupByType(){
       return taskRepository.getPercentageGroupByType();
    }
}

