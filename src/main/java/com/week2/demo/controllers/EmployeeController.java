package com.week2.demo.controllers;

import com.week2.demo.dto.EmployeeDTO;
import com.week2.demo.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    EmployeeController(EmployeeService emp){
        this.employeeService=emp;
    }
    @GetMapping(path = "/{employeeID}")
    public ResponseEntity<EmployeeDTO> getEmployeeId(@PathVariable(name = "employeeID") Long id){
        Optional<EmployeeDTO> employeeDTO=employeeService.getEmployeeByID(id);
        return employeeDTO
                .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
                .orElse(ResponseEntity.notFound().build());
//        EmployeeDTO employeeDTO=employeeService.getEmployeeByID(id);
//        if(employeeDTO==null)return ResponseEntity.notFound().build();
//        else return ResponseEntity.ok(employeeDTO);
    }
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false,name = "age") Integer age){
        return ResponseEntity.ok(employeeService.findAll());
    }
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Valid EmployeeDTO emp){
        return new ResponseEntity<>(employeeService.createEmployee(emp), HttpStatus.CREATED);
    }
    @PutMapping(path = "/{EmployeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO emp,@PathVariable(name = "EmployeeId") Long ID){
        return ResponseEntity.ok(employeeService.updateEmployeeByID(emp,ID));
    }
    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable(name = "employeeId")Long id){
        Boolean gotDeleted=employeeService.deleteEmployeeById(id);
        if(gotDeleted)return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }
    @PatchMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@PathVariable Long employeeId, @RequestBody Map<String, Object> updates){
        EmployeeDTO employeeDTO=employeeService.updatePartialEmployeeById(employeeId,updates);
        if (employeeDTO==null)return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);
    }
}
