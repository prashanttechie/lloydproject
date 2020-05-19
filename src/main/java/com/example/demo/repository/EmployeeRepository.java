package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.Employee;

import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    List<Employee> findByPlace(String place);

    List<Employee> findByBusinessUnit(String businessUnit);

    List<Employee> findBySupervisorID(Long supervisorID);

    List<Employee> findByTitle(String title);
}