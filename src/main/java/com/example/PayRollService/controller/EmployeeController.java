package com.example.PayRollService.controller;

import com.example.PayRollService.entity.Employee;
import com.example.PayRollService.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/emp")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees()
    {
         List<Employee> allEmployee = employeeService.getAllEmployee();
         return new ResponseEntity<>(allEmployee, HttpStatus.FOUND);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id)
    {
         Employee employeeById = employeeService.getEmployeeById(id);
         return new ResponseEntity<>(employeeById,HttpStatus.FOUND);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Employee> getEmployeeByName(@PathVariable String name)
    {
         Employee employeeByName = employeeService.getEmployeeByName(name);
         return new ResponseEntity<>(employeeByName,HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee newemployee){
        if(newemployee==null){
            return new ResponseEntity<>(newemployee,HttpStatus.BAD_REQUEST);
        }
         Employee employee = employeeService.addEmployee(newemployee);
         return new ResponseEntity<>(employee,HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id,@RequestBody Employee updateEmployee){
        if (updateEmployee==null){
            return new ResponseEntity<>(updateEmployee,HttpStatus.OK);
        }
         Employee employee = employeeService.updateEmployee(id, updateEmployee);
         return new ResponseEntity<>(employee,HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id)
    {
         String s = employeeService.deleteEmployee(id);
         return new ResponseEntity<>(s,HttpStatus.OK);
    }
}
