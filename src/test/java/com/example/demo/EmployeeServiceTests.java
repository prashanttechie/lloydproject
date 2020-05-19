package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmployeeServiceTests {

    private EmployeeRepository employeeRepository;
    private List<Employee> employeeList;
    private EmployeeService employeeService;

    @Test
    public void shouldReturnIncreasedSalary() {
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeService(employeeRepository);
        employeeList = new ArrayList<>();
        employeeList.add(new Employee(123L, "Prashant", "SDE", "engineering", "bengaluru", 321L, "java", 2000000L));
        employeeList.add(new Employee(222L, "Deepak", "ops", "support", "bengaluru", 111L, "sql", 700000L));
        when(employeeRepository.findByPlace(anyString())).thenReturn(employeeList);
        when(employeeRepository.saveAll(anyList())).thenReturn(null);
        List<Employee> resultList = employeeService.increaseSalaryOfEmployee(employeeList, 10);
        assertEquals(resultList.get(0).getSalary(), 2200000);
    }

    @Test
    public void shouldReturnEmployeeHierarchy() {
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeService(employeeRepository);
        employeeList = new ArrayList<>();
        employeeList.add(new Employee(123L, "Prashant", "SDE", "engineering", "bengaluru", 321L, "java", 2000000L));
        employeeList.add(new Employee(222L, "Deepak", "ops", "support", "bengaluru", 111L, "sql", 700000L));
        employeeList.add(new Employee(111L, "Anju", "analyst", "finance", "gurgaon", 123L, "excel", 1500000L));

        employeeList.add(new Employee(333L, "Ravi", "SDE", "engineering", "hydrabad", 111L, "python", 800000L));
        employeeList.add(new Employee(321L, "kavita", "analyst", "finance", "noida", 321L, "hr", 1000000L));
        when(employeeRepository.findAll()).thenReturn(employeeList);
        String result = employeeService.getSuperviserHierarchy(111L);
        assertEquals(result.trim(), "111->222,333,");
    }

    @Test
    public void testTotalSalaryOfBU() {
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeService(employeeRepository);
        employeeList = new ArrayList<>();
        employeeList.add(new Employee(123L, "Prashant", "SDE", "engineering", "bengaluru", 321L, "java", 2000000L));
        employeeList.add(new Employee(333L, "Ravi", "SDE", "engineering", "hydrabad", 111L, "python", 800000L));
        when(employeeRepository.findByBusinessUnit(anyString())).thenReturn(employeeList);
        Long result = employeeService.getTotalSalaryOfBU("engineering");
        assertEquals(result, 2800000L);
    }

    @Test
    public void testTotalSalaryOfPlace() {
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeService(employeeRepository);
        employeeList = new ArrayList<>();
        employeeList.add(new Employee(123L, "Prashant", "SDE", "engineering", "bengaluru", 321L, "java", 2000000L));
        employeeList.add(new Employee(222L, "Deepak", "ops", "support", "bengaluru", 111L, "sql", 700000L));
        when(employeeRepository.findByPlace(anyString())).thenReturn(employeeList);
        Long result = employeeService.getTotalSalaryOfPlace("bengaluru");
        assertEquals(result, 2700000L);
    }

    @Test
    public void testTotalSalaryOfSupervisor() {
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeService(employeeRepository);
        employeeList = new ArrayList<>();
        employeeList.add(new Employee(333L, "Ravi", "SDE", "engineering", "hydrabad", 111L, "python", 800000L));
        employeeList.add(new Employee(222L, "Deepak", "ops", "support", "bengaluru", 111L, "sql", 700000L));
        when(employeeRepository.findBySupervisorID(anyLong())).thenReturn(employeeList);
        Long result = employeeService.getTotalSalaryOfSupervisor(111L);
        assertEquals(result, 1500000L);
    }

    @Test
    public void testRangeOfSalaryOfTitle() {
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeService(employeeRepository);
        employeeList = new ArrayList<>();
        employeeList.add(new Employee(321L, "kavita", "analyst", "finance", "noida", 321L, "hr", 1000000L));
        employeeList.add(new Employee(111L, "Anju", "analyst", "finance", "gurgaon", 123L, "excel", 1500000L));
        when(employeeRepository.findByTitle(anyString())).thenReturn(employeeList);
        String result = employeeService.getRangeOfSalaryOfTitle("analyst");
        assertEquals(result.trim(), "1000000-1500000");
    }

}