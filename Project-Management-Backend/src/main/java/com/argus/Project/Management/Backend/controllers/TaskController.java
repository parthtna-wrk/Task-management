package com.argus.Project.Management.Backend.controllers;

import com.argus.Project.Management.Backend.DTO.CountType;
import com.argus.Project.Management.Backend.model.Task;
import com.argus.Project.Management.Backend.services.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
@AllArgsConstructor
public class TaskController {

    private TaskService taskService;

    @GetMapping("/task")
    public List<Task> getTask() {
        return taskService.getTasks();
    }

    @GetMapping("/task/vData/percentcounttype")
    public List<CountType> getPercentageGroupByType()

    {
        return taskService.getPercentageGroupByType();
    }


    @PostMapping("/task")
    public Task addTask(@RequestBody Task task){
        return taskService.save(task);
    }
    @GetMapping("/task/{id}")
    public Task getById(@PathVariable Long id){
        return taskService.getTaskById(id).orElseThrow(() -> new EntityNotFoundException("Task not found"));
    }
    @PutMapping("/task/{id}")
    public ResponseEntity<?> updateTask(@RequestBody Task taskPara,@PathVariable Long id) {
        if (taskService.existsById(id)) {
            Task task = taskService.getTaskById(id).orElseThrow(() -> new EntityNotFoundException("Task not found"));
            task.setTitle(taskPara.getTitle());
            task.setType(taskPara.getType());
            task.setDueDate(taskPara.getDueDate());
            task.setDescription(taskPara.getDescription());

            taskService.save(task);
            return ResponseEntity.ok().body(task);

        } else {

            HashMap<String, String> message = new HashMap<>();
            message.put("message", id + " task not found or matched");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
    @DeleteMapping("/task/{id}")
        public ResponseEntity<?> deleteTask(@PathVariable Long id){
            if (taskService.existsById(id)){
                taskService.delete(id);

                HashMap<String,String>message =new HashMap<>();
                message.put("message",id+" task deleted successfully");
                return ResponseEntity.status(HttpStatus.OK).body(message);

            }else {

                HashMap<String,String>message =new HashMap<>();
                message.put("message",id+" task not found or matched");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
            }

    }
}
