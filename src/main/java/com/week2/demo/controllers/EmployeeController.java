package com.week2.demo.controllers;

import com.week2.demo.dto.EmployeeDTO;
import com.week2.demo.entities.EmployeeEntity;
import com.week2.demo.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    EmployeeController(EmployeeService emp){
        this.employeeService=emp;
    }
    @GetMapping(path = "/{employeeID}")
    public EmployeeDTO getEmployeeId(@PathVariable(name = "employeeID") Long id){
        return employeeService.getEmployeeByID(id);
    }
    @GetMapping
    public List<EmployeeDTO> getAllEmployees(@RequestParam(required = false,name = "age") Integer age){
        return employeeService.findAll();
    }
    @PostMapping
    public EmployeeDTO createEmployee(@RequestBody EmployeeDTO emp){
        return this.employeeService.createEmployee(emp);
    }
    @PutMapping
    public EmployeeDTO updateEmployee(@RequestBody EmployeeDTO emp){
        return emp;
    }

}
