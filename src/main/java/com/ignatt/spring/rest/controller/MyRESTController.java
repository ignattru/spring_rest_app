package com.ignatt.spring.rest.controller;

import com.ignatt.spring.rest.entity.Employee;
import com.ignatt.spring.rest.exception_handing.EmployeeIncorrectData;
import com.ignatt.spring.rest.exception_handing.NoSuchEmployeeException;
import com.ignatt.spring.rest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MyRESTController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<Employee>showAllEmployees(){
        List<Employee> allEmployees = employeeService.getAllEmployees();
        return allEmployees;
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable int id){
        Employee employee = employeeService.getEmployee(id);

        if (employee == null){
            throw new NoSuchEmployeeException("No data for this req");
        }
        return employee;
    }
    @ExceptionHandler
    public ResponseEntity<EmployeeIncorrectData> handleException(
            NoSuchEmployeeException exception) {
        EmployeeIncorrectData date = new EmployeeIncorrectData();
        date.setInfo(exception.getMessage());
        return new ResponseEntity<>(date, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/employees")
    public Employee addNewEmployee(@RequestBody Employee employee){

        employeeService.saveEmployee(employee);
        return employee;
    }

}
