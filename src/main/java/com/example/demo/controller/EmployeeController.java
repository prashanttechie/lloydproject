package com.example.demo.controller;

import java.util.List;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
    EmployeeService employeeService;

    @Autowired
    EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "/employee/place/{place}/percentage/{percentage}", method = RequestMethod.PUT)
    public List<Employee> increaseSalary(@PathVariable("place") String place,
            @PathVariable("percentage") Integer percentage) {
        return employeeService.increaseSalaryOfEmployee(employeeService.getEmployeeByPlace(place), percentage);
    }

    @RequestMapping(value = "/hierarchy/supervisor/{employeeId}", method = RequestMethod.GET)
    public String getSuperviserHierarchy(@PathVariable("employeeId") Long employeeId) {
        return employeeService.getSuperviserHierarchy(employeeId);
    }

    @RequestMapping(value = "/employee/cache/place/{place}", method = RequestMethod.GET)
    public List<Employee> fetchEmployeeFromCache(@PathVariable("place") String place) {
        return employeeService.getEmployeeByPlaceFromCache(place);
    }

    @RequestMapping(value = "/sum/salary/bussinessUnit/{bussinessUnit}", method = RequestMethod.GET)
    public Long getTotalSalaryOfBU(@PathVariable("bussinessUnit") String bussinessUnit) {
        return employeeService.getTotalSalaryOfBU(bussinessUnit);
    }

    @RequestMapping(value = "/sum/salary/supervisor/{supervisorId}", method = RequestMethod.GET)
    public Long getTotalSalaryOfSupervisor(@PathVariable("supervisorId") Long supervisorId) {
        return employeeService.getTotalSalaryOfSupervisor(supervisorId);
    }

    @RequestMapping(value = "/sum/salary/place/{place}", method = RequestMethod.GET)
    public Long getTotalSalaryOfPlace(@PathVariable("place") String place) {
        return employeeService.getTotalSalaryOfPlace(place);
    }

    @RequestMapping(value = "/range/salary/{title}", method = RequestMethod.GET)
    public String getRangeOfSalaryOfTitle(@PathVariable("title") String title) {
        return employeeService.getRangeOfSalaryOfTitle(title);
    }

}