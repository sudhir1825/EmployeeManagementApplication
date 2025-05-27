package com.example.EmployeeManagementApplication.Controller;

import com.example.EmployeeManagementApplication.Entity.EmployeeEntity;
import com.example.EmployeeManagementApplication.Exception.EmailAlreadyExistsException;

import com.example.EmployeeManagementApplication.Service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService service;

    @Test
    public void testDisplayAllEmployees() throws Exception {
        EmployeeEntity emp = new EmployeeEntity("1", "John", "Doe", "john@example.com");
        Mockito.when(service.getAllEmployees()).thenReturn(List.of(emp));

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("employeeEntity"));
    }

    @Test
    public void testAddEmployeeForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/addEmployee"))
                .andExpect(status().isOk())
                .andExpect(view().name("addEmployee"))
                .andExpect(model().attributeExists("employeeEntity"));
    }

    @Test
    public void testAddEmployee_Success() throws Exception {
        Mockito.doNothing().when(service).addEmployee(any(EmployeeEntity.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/addEmployee")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("EmployeeFirstName", "John")
                        .param("EmployeeLastName", "Doe")
                        .param("employeeEmailId", "john@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testAddEmployee_EmailAlreadyExists() throws Exception {
        Mockito.doThrow(new EmailAlreadyExistsException("Email already exists"))
                .when(service).addEmployee(any(EmployeeEntity.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/addEmployee")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("EmployeeFirstName", "John")
                        .param("EmployeeLastName", "Doe")
                        .param("employeeEmailId", "john@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/addEmployee"));
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        Mockito.doNothing().when(service).deleteEmployee("1");

        mockMvc.perform(MockMvcRequestBuilders.post("/employees/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testViewEmployee() throws Exception {
        EmployeeEntity emp = new EmployeeEntity("1", "John", "Doe", "john@example.com");
        Mockito.when(service.getEmployeeById("1")).thenReturn(emp);

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/view/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("view"))
                .andExpect(model().attributeExists("employee"));
    }

    @Test
    public void testUpdateEmployeeForm() throws Exception {
        EmployeeEntity emp = new EmployeeEntity("1", "John", "Doe", "john@example.com");
        Mockito.when(service.getEmployeeById("1")).thenReturn(emp);

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("update"))
                .andExpect(model().attributeExists("employee"));
    }

    @Test
    public void testUpdateEmployee_Success() throws Exception {
        Mockito.doNothing().when(service).updateEmployee(Mockito.anyString(), any(EmployeeEntity.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/employees/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("EmployeeFirstName", "Jane")
                        .param("EmployeeLastName", "Smith")
                        .param("employeeEmailId", "jane@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
