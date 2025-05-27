package com.example.EmployeeManagementApplication.Entity;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Employee_Entity")
public class EmployeeEntity {
    @Id
    private String id;
    @NotBlank
    private String EmployeeFirstName;
    @NotBlank
    private String EmployeeLastName;
    @Indexed(unique = true)
    private String employeeEmailId;

    public EmployeeEntity() {
    }

    public EmployeeEntity(String id, String employeeFirstName, String employeeLastName, String employeeEmailId) {
        this.id = id;
        EmployeeFirstName = employeeFirstName;
        EmployeeLastName = employeeLastName;
        this.employeeEmailId = employeeEmailId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeFirstName() {
        return EmployeeFirstName;
    }

    public void setEmployeeFirstName(String employeeFirstName) {
        EmployeeFirstName = employeeFirstName;
    }

    public String getEmployeeLastName() {
        return EmployeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        EmployeeLastName = employeeLastName;
    }

    public String getEmployeeEmailId() {
        return employeeEmailId;
    }

    public void setEmployeeEmailId(String employeeEmailId) {
        this.employeeEmailId = employeeEmailId;
    }
}
