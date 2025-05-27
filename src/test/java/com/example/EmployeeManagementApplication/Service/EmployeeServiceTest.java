package com.example.EmployeeManagementApplication.Service;

import com.example.EmployeeManagementApplication.Entity.EmployeeEntity;
import com.example.EmployeeManagementApplication.Exception.EmailAlreadyExistsException;
import com.example.EmployeeManagementApplication.Repository.EmpRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService service;

    @Mock
    private EmpRepository empRepository;

    private EmployeeEntity mockEmployee;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockEmployee = new EmployeeEntity("1", "John", "Doe", "john@example.com");
    }

    @Test
    public void testAddEmployee_Success() {
        when(empRepository.findByEmployeeEmailId(mockEmployee.getEmployeeEmailId()))
                .thenReturn(Optional.empty());
        when(empRepository.save(mockEmployee)).thenReturn(mockEmployee);

        assertDoesNotThrow(() -> service.addEmployee(mockEmployee));
        verify(empRepository).save(mockEmployee);
    }

    @Test
    public void testAddEmployee_EmailAlreadyExists() {
        when(empRepository.findByEmployeeEmailId(mockEmployee.getEmployeeEmailId()))
                .thenReturn(Optional.of(mockEmployee));

        EmailAlreadyExistsException exception = assertThrows(
                EmailAlreadyExistsException.class,
                () -> service.addEmployee(mockEmployee)
        );

        assertEquals("Email already exists: john@example.com", exception.getMessage());
    }

    @Test
    public void testGetAllEmployees() {
        when(empRepository.findAll()).thenReturn(List.of(mockEmployee));
        List<EmployeeEntity> result = service.getAllEmployees();

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getEmployeeFirstName());
    }

    @Test
    public void testGetEmployeeById_Found() {
        when(empRepository.findById("1")).thenReturn(Optional.of(mockEmployee));
        EmployeeEntity result = service.getEmployeeById("1");

        assertNotNull(result);
        assertEquals("john@example.com", result.getEmployeeEmailId());
    }

    @Test
    public void testGetEmployeeById_NotFound() {
        when(empRepository.findById("2")).thenReturn(Optional.empty());
        EmployeeEntity result = service.getEmployeeById("2");

        assertNull(result);
    }

    @Test
    public void testUpdateEmployee_Success() {
        EmployeeEntity updated = new EmployeeEntity("1", "Jane", "Smith", "jane@example.com");

        when(empRepository.findById("1")).thenReturn(Optional.of(mockEmployee));
        when(empRepository.findByEmployeeEmailId("jane@example.com")).thenReturn(Optional.empty());
        when(empRepository.save(any())).thenReturn(updated);

        EmployeeEntity result = service.updateEmployee("1", updated);

        assertEquals("Jane", result.getEmployeeFirstName());
        assertEquals("jane@example.com", result.getEmployeeEmailId());
    }

    @Test
    public void testUpdateEmployee_EmailAlreadyExists() {
        EmployeeEntity existing = new EmployeeEntity("2", "Other", "User", "jane@example.com");
        EmployeeEntity updated = new EmployeeEntity("1", "Jane", "Smith", "jane@example.com");

        when(empRepository.findById("1")).thenReturn(Optional.of(mockEmployee));
        when(empRepository.findByEmployeeEmailId("jane@example.com")).thenReturn(Optional.of(existing));

        assertThrows(EmailAlreadyExistsException.class,
                () -> service.updateEmployee("1", updated));
    }

    @Test
    public void testDeleteEmployee() {
        doNothing().when(empRepository).deleteById("1");
        assertDoesNotThrow(() -> service.deleteEmployee("1"));
        verify(empRepository).deleteById("1");
    }
}

