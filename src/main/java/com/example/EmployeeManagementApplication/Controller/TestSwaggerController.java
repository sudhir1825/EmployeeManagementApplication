package com.example.EmployeeManagementApplication.Controller;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestSwaggerController {

    @Operation(summary = "Test endpoint")
    @GetMapping("/api/test")
    public String test() {
        return "swagger works";
    }
}
