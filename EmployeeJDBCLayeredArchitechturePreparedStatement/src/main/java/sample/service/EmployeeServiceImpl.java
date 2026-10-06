package sample.service;

import java.util.List;

import sample.EmployeeBean;
import sample.dao.EmployeeDAOImpl;
import sample.dao.EmployeeDao;

public class EmployeeServiceImpl implements EmployeeService {
	EmployeeDao employeeDAO = new EmployeeDAOImpl();

	@Override
	public void updateEmployee(EmployeeBean bean) {
		// TODO Auto-generated method stub

		employeeDAO.updateEmployee(bean);

	}

	
	public int addEmployee(EmployeeBean employee) {
		// TODO Auto-generated method stub
		int rowsUpdateCount = 0;

		rowsUpdateCount = employeeDAO.addEmployee(employee);

		return rowsUpdateCount;
	}

	public List<EmployeeBean> getEmployeeList() {
		// TODO Auto-generated method stub

		return employeeDAO.getEmployeeList();

	}

	@Override
	public EmployeeBean getEmployeeById(int employeeId) {
		// TODO Auto-generated method stub
		EmployeeBean emp = null;
		emp = employeeDAO.getEmployeeById(employeeId);
		return emp;
	}

	@Override
	public void deleteEmployee(int employeeId) {
		// TODO Auto-generated method stub

		employeeDAO.deleteEmployee(employeeId);

	}
}