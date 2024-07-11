package com.week2.demo.controllers;

import com.week2.demo.dto.EmployeeDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    @GetMapping(path = "/{employeeID}")
    public EmployeeDTO getEmployeeId(@PathVariable(name = "employeeID") Long id){
        return new EmployeeDTO(id,"pnd","abc@.com",12, LocalDate.now(),true);
    }
    @GetMapping
    public String getAllEmployees(@RequestParam(required = false,name = "age") int age){
        return "All Employees "+age;
    }
    @PostMapping
    public EmployeeDTO createEmployee(@RequestBody EmployeeDTO emp){
        emp.setId(100L);
        return emp;
    }
    @PutMapping
    public EmployeeDTO updateEmployee(@RequestBody EmployeeDTO emp){
        emp.setId(100L);
        return emp;
    }

}
