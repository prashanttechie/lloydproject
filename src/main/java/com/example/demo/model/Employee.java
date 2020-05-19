package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

import javax.persistence.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee_details")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long employeeID;
    @NonNull
    private String employeeName;
    private String title;
    private String businessUnit;
    private String place;
    private Long supervisorID;
    private String competencies;
    private Long salary;
}