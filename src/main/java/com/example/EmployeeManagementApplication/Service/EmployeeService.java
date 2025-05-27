package com.example.EmployeeManagementApplication.Service;
import com.example.EmployeeManagementApplication.Exception.EmailAlreadyExistsException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.example.EmployeeManagementApplication.Entity.EmployeeEntity;
import com.example.EmployeeManagementApplication.Repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements EmpService{
    @Autowired
    private EmpRepository empRepository;

    @Override
    public Void addEmployee(EmployeeEntity employeeEntity){
        Optional<EmployeeEntity> existing = empRepository.findByEmployeeEmailId(employeeEntity.getEmployeeEmailId());
        if (existing.isPresent() && (employeeEntity.getId() == null || !existing.get().getId().equals(employeeEntity.getId()))) {
            throw new EmailAlreadyExistsException("Email already exists: " + employeeEntity.getEmployeeEmailId());
        }
        System.out.println("Saving employee: " + employeeEntity);
        EmployeeEntity saved = empRepository.save(employeeEntity);
        System.out.println("Saved employee: " + saved);

        return null;

    }
    @Override
    public List<EmployeeEntity> getAllEmployees() {
        return empRepository.findAll();
    }
    @Override
    public EmployeeEntity getEmployeeById(String id) {
        return empRepository.findById(id).orElse(null);
    }
    @Override
    public EmployeeEntity updateEmployee(String id, EmployeeEntity employeeData) {

        EmployeeEntity employee = empRepository.findById(id).orElse(null);
        if (employee != null) {
            employee.setEmployeeFirstName(employeeData.getEmployeeFirstName());
            employee.setEmployeeLastName(employeeData.getEmployeeLastName());
            Optional<EmployeeEntity> existing = empRepository.findByEmployeeEmailId(employeeData.getEmployeeEmailId());
            if (existing.isPresent() && (employeeData.getId() == null || !existing.get().getId().equals(employeeData.getId()))) {
                throw new EmailAlreadyExistsException("Email already exists: " + employeeData.getEmployeeEmailId());
            }
            employee.setEmployeeEmailId(employeeData.getEmployeeEmailId());
            return empRepository.save(employee);
        }
        return null;
    }

    @Override
    public Void deleteEmployee(String id){
        empRepository.deleteById(id);
        return null;
    }
}
