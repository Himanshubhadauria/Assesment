package com.EmployeeManagementSystem.Service;

import com.EmployeeManagementSystem.Entity.Employee;
import com.EmployeeManagementSystem.Repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private
    EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        validateId(id);
        return employeeRepository.findById(id);
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }
    }

    public Employee addEmployee(Employee employee) {
        validateEmployee(employee);
        employee.setJoiningDate(new Date());
        return employeeRepository.save(employee);
    }

    private void validateEmployee(Employee employee) {
        if (employee == null || employee.getName().isEmpty() || employee.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Employee data is incomplete or invalid");
        }
    }
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        if (employeeRepository.existsById(id)) {
            updatedEmployee.setId(id);
            return employeeRepository.save(updatedEmployee);
        } else {
            throw new IllegalArgumentException("Employee not found with id: " + id);
        }
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
