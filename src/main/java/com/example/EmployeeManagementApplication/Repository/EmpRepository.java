package com.example.EmployeeManagementApplication.Repository;

import com.example.EmployeeManagementApplication.Entity.EmployeeEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EmpRepository extends MongoRepository<EmployeeEntity, String> {
    // Custom finder method to check if email already exists
    Optional<EmployeeEntity> findByEmployeeEmailId(String employeeEmailId);
}
