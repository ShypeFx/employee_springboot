package com.example.employee_server.controller;

import com.example.employee_server.model.Employee;
import com.example.employee_server.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/employees")
    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/employees/{id}")
    public Employee findById(@PathVariable("id") Integer id){
        return employeeRepository.findById(id).get();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/create")
    public Employee createEmployee(@RequestBody Employee employee){
        if(employee.isEmpty()){
            return null;
        }else{
            return this.employeeRepository.save(employee);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/update/{id}")
    public void updateEmployee(@PathVariable Integer id, @RequestBody Employee employeeDetails){
        Employee employee = employeeRepository.findById(id).get();

        employee.setName(employeeDetails.getName());
        employee.setFirstname(employeeDetails.getFirstname());
        employee.setPoste(employeeDetails.getPoste());
        employee.setEmail(employeeDetails.getEmail());

        Employee update = employeeRepository.save(employee);

    }


    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Integer id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


}
