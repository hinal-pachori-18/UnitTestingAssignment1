package com.example.PayRollService.service;

import com.example.PayRollService.entity.Employee;

import java.util.List;

public interface EmployeeService {
    public List<Employee> getAllEmployee();
    public Employee getEmployeeByName(String name);
    public Employee getEmployeeById(int id);
    public Employee addEmployee(Employee employee);
    public Employee     updateEmployee(int id,Employee employee);
    public String deleteEmployee(int id);
}
