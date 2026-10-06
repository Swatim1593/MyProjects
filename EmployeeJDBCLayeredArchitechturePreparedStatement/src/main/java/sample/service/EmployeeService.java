package sample.service;

import java.util.List;

import sample.EmployeeBean;

public interface EmployeeService {
	int addEmployee(EmployeeBean employee);

	List<EmployeeBean> getEmployeeList();

	EmployeeBean getEmployeeById(int employeeId);

	void updateEmployee(EmployeeBean employee);

	void deleteEmployee(int employeeId);

}