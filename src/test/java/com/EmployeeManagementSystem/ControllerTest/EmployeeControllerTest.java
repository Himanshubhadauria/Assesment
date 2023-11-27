package com.EmployeeManagementSystem.ControllerTest;

import com.EmployeeManagementSystem.Controller.EmployeeController;
import com.EmployeeManagementSystem.Entity.Employee;
import com.EmployeeManagementSystem.Service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    void testGetAllEmployees() {
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(employee1, employee2));

        assertEquals(2, employeeController.getAllEmployees().size());

        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    void testGetEmployeeById() {
        Employee employee = new Employee();
        when(employeeService.getEmployeeById(1L)).thenReturn(Optional.of(employee));

        ResponseEntity<Employee> responseEntity = employeeController.getEmployeeById(1L);

        verify(employeeService, times(1)).getEmployeeById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(employee, responseEntity.getBody());
    }


    @Test
    void testAddEmployee() {
        Employee employee = new Employee();
        when(employeeService.addEmployee(any())).thenReturn(employee);

        ResponseEntity<Employee> responseEntity = employeeController.addEmployee(employee);

        verify(employeeService, times(1)).addEmployee(any());

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(employee, responseEntity.getBody());
    }

    @Test
    void testUpdateEmployee() {
        Long employeeId = 1L;
        Employee existingEmployee = new Employee();
        when(employeeService.getEmployeeById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(employeeService.updateEmployee(eq(employeeId), any())).thenReturn(existingEmployee);

        ResponseEntity<Employee> responseEntity = employeeController.updateEmployee(employeeId, existingEmployee);

        verify(employeeService, times(1)).getEmployeeById(employeeId);
        verify(employeeService, times(1)).updateEmployee(eq(employeeId), any());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(existingEmployee, responseEntity.getBody());
    }

    @Test
    void testUpdateEmployeeNotFound() {
        Long employeeId = 1L;
        when(employeeService.getEmployeeById(employeeId)).thenReturn(Optional.empty());

        ResponseEntity<Employee> responseEntity = employeeController.updateEmployee(employeeId, new Employee());

        verify(employeeService, times(1)).getEmployeeById(employeeId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testDeleteEmployee() {
        Long employeeId = 1L;

        ResponseEntity<Void> responseEntity = employeeController.deleteEmployee(employeeId);

        verify(employeeService, times(1)).deleteEmployee(employeeId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

}