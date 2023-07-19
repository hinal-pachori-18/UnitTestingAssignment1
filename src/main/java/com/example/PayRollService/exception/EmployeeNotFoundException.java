package com.example.PayRollService.exception;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException(){
        super("Employee not present");
    }
    public EmployeeNotFoundException(int id) {
        super("Could not find this  id :"+id);
    }
    public EmployeeNotFoundException(String name){
        super("Could not find this name :"+name);
    }
}
