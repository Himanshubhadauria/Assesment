package com.EmployeeManagementSystemServiceTest;

import com.EmployeeManagementSystem.Entity.Employee;
import com.EmployeeManagementSystem.Repo.EmployeeRepository;
import com.EmployeeManagementSystem.Service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEmployees() {
        // Mock data
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));

        // Test the service method
        List<Employee> employees = employeeService.getAllEmployees();

        // Verify that the repository method was called
        verify(employeeRepository, times(1)).findAll();

        // Verify the result
        assertThat(employees).hasSize(2);
    }

    @Test
    void testGetEmployeeById() {
        // Mock data
        Long employeeId = 1L;
        Employee employee = new Employee();
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        // Test the service method
        Optional<Employee> result = employeeService.getEmployeeById(employeeId);

        // Verify that the repository method was called
        verify(employeeRepository, times(1)).findById(employeeId);

        // Verify the result
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(employee);
    }

    @Test
    void testAddEmployee() {
        // Mock data
        Employee employee = new Employee();
        when(employeeRepository.save(employee)).thenReturn(new Employee());

        // Test the service method
        Employee result = employeeService.addEmployee(employee);

        // Verify that the repository method was called
        verify(employeeRepository, times(1)).save(employee);

        // Verify the result
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo("John Doe");
        assertThat(result.getEmail()).isEqualTo("john@example.com");
        assertThat(result.getDepartment()).isEqualTo("IT");
        assertThat(result.getJoiningDate()).isNotNull();
    }

    @Test
    void testUpdateEmployee() {
        Long employeeId = 1L;
        Employee existingEmployee = new Employee();
        Employee updatedEmployee = new Employee();
        when(employeeRepository.existsById(employeeId)).thenReturn(true);
        when(employeeRepository.save(updatedEmployee)).thenReturn(updatedEmployee);

        Employee result = employeeService.updateEmployee(employeeId, updatedEmployee);

        verify(employeeRepository, times(1)).existsById(employeeId);
        verify(employeeRepository, times(1)).save(updatedEmployee);

        assertThat(result).isEqualTo(updatedEmployee);
    }

    @Test
    void testUpdateEmployeeNotFound() {
        // Mock data
        Long employeeId = 1L;
        when(employeeRepository.existsById(employeeId)).thenReturn(false);

        // Test the service method for the case where the employee is not found
        assertThatThrownBy(() -> employeeService.updateEmployee(employeeId, new Employee()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Employee not found with id: " + employeeId);
        verify(employeeRepository, times(1)).existsById(employeeId);
    }

    @Test
    void testDeleteEmployee() {
        Long employeeId = 1L;

        employeeService.deleteEmployee(employeeId);

        verify(employeeRepository, times(1)).deleteById(employeeId);
    }

}
