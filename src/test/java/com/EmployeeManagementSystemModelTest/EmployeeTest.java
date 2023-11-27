package com.EmployeeManagementSystemModelTest;

import com.EmployeeManagementSystem.Entity.Employee;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;

class EmployeeTest {

    @Test
    void testCreateEmployeeWithValidData() {
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setEmail("john@example.com");
        employee.setDepartment("IT");
        employee.setJoiningDate(new Date());

        assertThatCode(() -> {
            employee.setId(1L); // ID is typically set by the database, but for the test, we set it manually
            assertThat(employee.getId()).isEqualTo(1L);
            assertThat(employee.getName()).isEqualTo("John Doe");
            assertThat(employee.getEmail()).isEqualTo("john@example.com");
            assertThat(employee.getDepartment()).isEqualTo("IT");
            assertThat(employee.getJoiningDate()).isNotNull();
        }).doesNotThrowAnyException();
    }

    @Test
    void testCreateEmployeeWithBlankName() {
        Employee employee = new Employee();
        employee.setEmail("john@example.com");
        employee.setDepartment("IT");
        employee.setJoiningDate(new Date());

        assertThatThrownBy(() -> employee.setId(1L)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void testCreateEmployeeWithInvalidEmail() {
        // Create an employee with an invalid email
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setEmail("invalid-email");
        employee.setDepartment("IT");
        employee.setJoiningDate(new Date());

        assertThatThrownBy(() -> employee.setId(1L)).isInstanceOf(ConstraintViolationException.class);
    }

}
