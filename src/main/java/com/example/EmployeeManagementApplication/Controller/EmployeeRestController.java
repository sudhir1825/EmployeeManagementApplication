package com.example.EmployeeManagementApplication.Controller;

import com.example.EmployeeManagementApplication.Entity.EmployeeEntity;
import com.example.EmployeeManagementApplication.Exception.EmailAlreadyExistsException;
import com.example.EmployeeManagementApplication.Service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee API", description = "Operations related to employees")
public class EmployeeRestController {

    private final EmployeeService service;

    @Autowired
    public EmployeeRestController(EmployeeService service) {
        this.service = service;
    }

    // Get all employees
    @GetMapping
    public ResponseEntity<List<EmployeeEntity>> getAllEmployees() {
        List<EmployeeEntity> employees = service.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    // Get employee by id
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeEntity> getEmployeeById(@PathVariable String id) {
        EmployeeEntity employee = service.getEmployeeById(id);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employee);
    }

    // Add new employee
    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeEntity employee) {
        try {
            service.addEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee added successfully");
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    // Update employee
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable String id, @RequestBody EmployeeEntity employeeData) {
        try {
            EmployeeEntity updated = service.updateEmployee(id, employeeData);
            if (updated == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok("Employee updated successfully");
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    // Delete employee
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String id) {
        service.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }
}
