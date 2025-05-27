package com.example.EmployeeManagementApplication.Controller;

import com.example.EmployeeManagementApplication.Entity.EmployeeEntity;
import com.example.EmployeeManagementApplication.Exception.EmailAlreadyExistsException;
import com.example.EmployeeManagementApplication.Service.EmployeeService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.*;


@Controller
public class EmployeeController {
    @Autowired
    private  EmployeeService service ;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/employees")
    public String display(Model model){
        model.addAttribute("employeeEntity",service.getAllEmployees());
        return "index";
    }

    @GetMapping("/addEmployee")
    public String addProductForm(Model model) {
        model.addAttribute("employeeEntity", new EmployeeEntity());
        return "addEmployee";// Show form
    }
    @PostMapping("/addEmployee")
    public String addEmployee(@ModelAttribute("employeeEntity") EmployeeEntity employee, RedirectAttributes redirectAttributes) {
        try {
            service.addEmployee(employee);
            redirectAttributes.addFlashAttribute("successMessage", "Employee added successfully!");
            return "redirect:/employees";
        } catch (EmailAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/addEmployee";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong. Please try again.");
            return "redirect:/addEmployee";
        }
    }




    @PostMapping("/employees/delete/{id}")
    public String deleteEmployee(@PathVariable("id") String id) {
        System.out.println("Deleting ID: " + id);
        service.deleteEmployee(id);
        return "redirect:/employees"; // Redirects back to index page after deletion
    }

    @GetMapping("/employees/view/{id}")
    public String viewEmployee(@PathVariable String id, Model model) {
        model.addAttribute("employee", service.getEmployeeById(id));
        return "view";
    }

    @GetMapping("/employees/update/{id}")
    public String updateEmployeeForm(@PathVariable String id, Model model) {
        model.addAttribute("employee", service.getEmployeeById(id));
        return "update";
    }

    @PostMapping("/employees/update/{id}")
    public String updateEmployee(
            @PathVariable String id,
            @Valid @ModelAttribute("employee") EmployeeEntity employeeData,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "update";
        }

        service.updateEmployee(id, employeeData);
        redirectAttributes.addFlashAttribute("successMessage", "Employee updated successfully.");
        return "redirect:/employees";
    }

}
