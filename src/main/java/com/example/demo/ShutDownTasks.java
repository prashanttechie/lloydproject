package com.example.demo;

import javax.annotation.PreDestroy;

import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShutDownTasks {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmployeeRepository employeeRepository;

    @PreDestroy
    public void destroy() {
        System.out.println("#### Fetching employee list from cache and persisting ########");

    }
}