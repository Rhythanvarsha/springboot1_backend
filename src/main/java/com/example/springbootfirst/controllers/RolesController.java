// RoleController.java
package com.example.springbootfirst.controllers;

import com.example.springbootfirst.models.Roles;
import com.example.springbootfirst.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*") // Enable CORS for frontend
public class RolesController {

    @Autowired
    private RolesRepository rolesRepository;

    @GetMapping
    public List<Roles> getAllRoles() {
        return rolesRepository.findAll();
    }
}
