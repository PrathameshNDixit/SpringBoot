package com.week2.demo.controllers;

import com.week2.demo.dto.EmployeeDTO;
import com.week2.demo.entities.EmployeeEntity;
import com.week2.demo.repositories.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {
    private final EmployeeRepository emp;
    EmployeeController(EmployeeRepository emp){
        this.emp=emp;
    }
    @GetMapping(path = "/{employeeID}")
    public EmployeeEntity getEmployeeId(@PathVariable(name = "employeeID") Long id){
        return emp.findById(id).orElse(null);
    }
    @GetMapping
    public List<EmployeeEntity> getAllEmployees(@RequestParam(required = false,name = "age") Integer age){
        return emp.findAll();
    }
    @PostMapping
    public EmployeeEntity createEmployee(@RequestBody EmployeeEntity emp){
        return this.emp.save(emp);
    }
    @PutMapping
    public EmployeeDTO updateEmployee(@RequestBody EmployeeDTO emp){
        emp.setId(100L);
        return emp;
    }

}
