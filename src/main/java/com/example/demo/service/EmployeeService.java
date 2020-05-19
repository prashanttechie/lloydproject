package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> increaseSalaryOfEmployee(List<Employee> employeeList, Integer percentage) {
        employeeList = employeeList.stream()
                .map(emp -> new Employee(emp.getEmployeeID(), emp.getEmployeeName(), emp.getTitle(),
                        emp.getBusinessUnit(), emp.getPlace(), emp.getSupervisorID(), emp.getCompetencies(),
                        emp.getSalary() * (100 + percentage) / 100))
                .collect(Collectors.toList());
        employeeRepository.saveAll(employeeList);
        return employeeList;
    }

    public String getSuperviserHierarchy(Long supervisorId) {

        HashMap<Long, List<Long>> supervisorHashMap = new HashMap<>();
        List<Long> list;
        for (Employee emp : employeeRepository.findAll()) {
            if (supervisorHashMap.containsKey(emp.getSupervisorID())) {
                list = supervisorHashMap.get(emp.getSupervisorID());
                list.add(emp.getEmployeeID());
            } else {
                list = new ArrayList<>();
                list.add(emp.getEmployeeID());
            }
            supervisorHashMap.put(emp.getSupervisorID(), list);
        }
        if (!supervisorHashMap.containsKey(supervisorId))
            return "";

        Queue<List<Long>> queue = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        sb.append(supervisorId).append("->");
        queue.add(supervisorHashMap.get(supervisorId));
        while (!queue.isEmpty()) {
            List<Long> sIdList = queue.remove();
            if (sIdList.size() == 1) {
                sb.append(sIdList.get(0)).append("->");
                if (supervisorHashMap.containsKey(sIdList.get(0)))
                    queue.add(supervisorHashMap.get(sIdList.get(0)));
            }
            if (sIdList.size() > 1) {
                for (Long id : sIdList) {
                    sb.append(id).append(",");
                }
                break;
            }
        }
        return sb.toString();
    }

    public Long getTotalSalaryOfBU(String bussinessUnit) {
        List<Employee> employeeList = employeeRepository.findByBusinessUnit(bussinessUnit);
        return getSumOfSalariesOfEmployees(employeeList);
    }

    public Long getTotalSalaryOfSupervisor(Long supervisorId) {
        List<Employee> employeeList = employeeRepository.findBySupervisorID(supervisorId);
        return getSumOfSalariesOfEmployees(employeeList);
    }

    public Long getTotalSalaryOfPlace(String place) {
        List<Employee> employeeList = employeeRepository.findByPlace(place);
        return getSumOfSalariesOfEmployees(employeeList);
    }

    public String getRangeOfSalaryOfTitle(String title) {
        List<Employee> employeeList = employeeRepository.findByTitle(title);
        Long min = Long.MAX_VALUE;
        for (Employee employee : employeeList) {
            min = Math.min(min, employee.getSalary());
        }
        Long max = Long.MIN_VALUE;
        for (Employee employee : employeeList) {
            max = Math.max(max, employee.getSalary());
        }
        return min.toString() + "-" + max.toString();
    }

    public Long getSumOfSalariesOfEmployees(List<Employee> employeeList) {
        Long totalSalary = 0L;
        for (Employee employee : employeeList) {
            totalSalary += employee.getSalary();
        }
        return totalSalary;
    }

    @CachePut(value = "employeeList")
    public List<Employee> getEmployeeByPlace(String place) {
        System.out.println("CachePut: from DB not from cache....");
        return employeeRepository.findByPlace(place);
    }

    @Cacheable(value = "employeeList")
    public List<Employee> getEmployeeByPlaceFromCache(String place) {
        System.out.println("Cacheable: from DB not from cache....");
        return employeeRepository.findByPlace(place);
    }
}