package com.example.PayRollService.service;

import com.example.PayRollService.entity.Employee;
import com.example.PayRollService.exception.EmployeeNotFoundException;
import com.example.PayRollService.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeByName(String name) {
         return employeeRepository.findAll().stream().filter(employee -> employee.getName().equals(name)).findFirst().
                 orElseThrow(()->{
                     throw new EmployeeNotFoundException(name);
                 });
    }

    @Override
    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id).orElseThrow(()->{
            throw new EmployeeNotFoundException(id);
        });
    }

    @Override
    public Employee addEmployee(Employee employee) {
            return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(int id, Employee newEmployee) {
       employeeRepository.findById(id).map(employee ->
       {
           employee.setName(newEmployee.getName());
           employee.setSalary(newEmployee.getSalary());
           return employeeRepository.save(employee);
       }).orElseGet(()->{
           newEmployee.setId(id);
           return employeeRepository.save(newEmployee);
       });
       return newEmployee;
    }

    @Override
    public String deleteEmployee(int id) {
        if (employeeRepository.findById(id).isPresent())
        {
            employeeRepository.deleteById(id);
            return "Successfully deleted";
        }
        else
        {
            return "Not Successfully deleted";
        }
    }
}
