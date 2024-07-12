package com.week2.demo.services;

import com.week2.demo.dto.EmployeeDTO;
import com.week2.demo.entities.EmployeeEntity;
import com.week2.demo.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    public EmployeeService(EmployeeRepository employeeRepository,ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper=modelMapper;
    }

    public EmployeeDTO getEmployeeByID(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }

    public List<EmployeeDTO> findAll() {
        List<EmployeeEntity> all = employeeRepository.findAll();
        return all.stream().map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDTO.class)).collect(Collectors.toList());
    }

    public EmployeeDTO createEmployee(EmployeeDTO emp) {
        EmployeeEntity empEntity = modelMapper.map(emp, EmployeeEntity.class);
        employeeRepository.save(empEntity);
        return emp;
    }


}
