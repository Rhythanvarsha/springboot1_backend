package com.example.springbootfirst.services;




import com.example.springbootfirst.models.RegisterDetails;
import com.example.springbootfirst.models.TodoEmployee;
import com.example.springbootfirst.repository.RegisterDetailsRepository;
import com.example.springbootfirst.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ToDoService {

    @Autowired
    private ToDoRepository taskRepo;

    @Autowired
    private RegisterDetailsRepository registerRepo;


    public String createTask(TodoEmployee task) {
        // Check if employee exists
        RegisterDetails employee = registerRepo.findById(task.getEmpId());
               // .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + task.getEmpId()));

        taskRepo.save(task);
        return "Task added successfully";
    }


    public List<TodoEmployee> getAllTasks() {
        return taskRepo.findAll();
    }


    public TodoEmployee getTaskById(int id) {
        return taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + id));
    }


    public List<TodoEmployee> getTasksByEmpId(int empId) {
        return taskRepo.findAllByEmpId(empId);
    }


    public String updateTask(int taskId, TodoEmployee updatedTask) {
        TodoEmployee existingTask = taskRepo.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));

        registerRepo.findById(updatedTask.getEmpId());
                //orElseThrow(() -> new RuntimeException("Employee not found with ID: " + updatedTask.getEmpId()));


        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setEmpId(updatedTask.getEmpId());

        taskRepo.save(existingTask);
        return "Task updated successfully";
    }


    public String deleteTask(int taskId) {
        taskRepo.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));
        taskRepo.deleteById(taskId);
        return "Task deleted successfully";
    }


}