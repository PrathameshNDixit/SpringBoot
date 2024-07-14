package com.week2.demo.services;

import com.week2.demo.dto.EmployeeDTO;
import com.week2.demo.entities.EmployeeEntity;
import com.week2.demo.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    public EmployeeService(EmployeeRepository employeeRepository,ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper=modelMapper;
    }

    public Optional<EmployeeDTO> getEmployeeByID(Long id) {
//        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
//        return modelMapper.map(employeeEntity, EmployeeDTO.class);
        return employeeRepository.findById(id).map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDTO.class));
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


    public EmployeeDTO updateEmployeeByID(EmployeeDTO emp, Long employeeId) {
        EmployeeEntity empEntity=modelMapper.map(emp,EmployeeEntity.class);
        empEntity.setId(employeeId);
        EmployeeEntity savedEmployee=employeeRepository.save(empEntity);
        return modelMapper.map(savedEmployee,EmployeeDTO.class);
    }
    public Boolean isEmployeeExistById(Long id){
        return employeeRepository.existsById(id);
    }

    public Boolean deleteEmployeeById(Long id) {
         Boolean exists=isEmployeeExistById(id);
         if(!exists)return false;
         employeeRepository.deleteById(id);
         return true;
    }

    public EmployeeDTO updatePartialEmployeeById(Long employeeId, Map<String,Object> updates) {
        Boolean exists = isEmployeeExistById(employeeId);
        if(!exists)return null;
        EmployeeEntity empEntity=employeeRepository.findById(employeeId).get();
        updates.forEach((field,value)->{
            Field fieldToBeUpdated= ReflectionUtils.findRequiredField(EmployeeEntity.class, field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated,empEntity,value);
        });
        return modelMapper.map(employeeRepository.save(empEntity),EmployeeDTO.class);
    }
}
