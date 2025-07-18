package com.example.springbootfirst.controllers;



import com.example.springbootfirst.models.TodoEmployee;
import com.example.springbootfirst.services.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/task")
@CrossOrigin("*")
public class ToDoController {

    @Autowired
    private ToDoService taskService;


    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public List<TodoEmployee> getAllTasks() {
        return taskService.getAllTasks();
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/tasks/{id}")
    public TodoEmployee getTaskById(@PathVariable int id) {
        return taskService.getTaskById(id);
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/employee/{empId}")
    public List<TodoEmployee> getTasksByEmployeeId(@PathVariable int empId) {
        return taskService.getTasksByEmpId(empId);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public String createTask(@RequestBody TodoEmployee task) {
        return taskService.createTask(task);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{taskId}")
    public String updateTask(@PathVariable int taskId, @RequestBody TodoEmployee task) {
        return taskService.updateTask(taskId, task);
    }




    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/task/{taskId}")
    public String deleteTask(@PathVariable int taskId) {
        return taskService.deleteTask(taskId);
    }

}