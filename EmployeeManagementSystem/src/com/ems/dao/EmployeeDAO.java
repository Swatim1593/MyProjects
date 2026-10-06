package com.ems.dao;

import java.util.List;

import com.ems.model.Employee;

public interface EmployeeDAO {
	void addEmployee(Employee emp);
	Employee getEmployeeById(int id);
	List<Employee> getAllEmployees();
	void updateEmployee(Employee emp);
	void deleteEmployee(int id);

}
