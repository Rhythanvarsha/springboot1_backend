package com.example.springbootfirst.repository;




import com.example.springbootfirst.models.TodoEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ToDoRepository extends JpaRepository<TodoEmployee,Integer> {


    void deleteByEmpId(int id);

    List<TodoEmployee> findAllByEmpId(int empId);
}