package com.example.EmployeeManagementApplication.Service;

import com.example.EmployeeManagementApplication.Entity.EmployeeEntity;

import java.util.List;

public interface EmpService {
    Void addEmployee(EmployeeEntity employeeEntity);
    List<EmployeeEntity> getAllEmployees();
    EmployeeEntity getEmployeeById(String id);
    EmployeeEntity updateEmployee(String id, EmployeeEntity employeeData);

    Void deleteEmployee(String id);
}
