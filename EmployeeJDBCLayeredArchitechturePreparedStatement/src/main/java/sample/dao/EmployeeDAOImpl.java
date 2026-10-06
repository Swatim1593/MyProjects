package sample.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sample.DBUtility;
import sample.EmployeeBean;

public class EmployeeDAOImpl implements EmployeeDao {
	private Connection getConnection() throws SQLException, ClassNotFoundException {
		return DBUtility.getDBConnection();
	}

	@Override
	public int addEmployee(EmployeeBean employee) {
	int rowsAffected=0;
		String sql = "insert into employee(employeeName,role,insertTime,salary)values(?,?,?,?)";

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, employee.getEmployeeName());
			pstmt.setString(2, employee.getRole());
			// pstmt.setTimestamp(3, employee.getInsertTime());
			Date utilDate = employee.getInsertTime();
			if (utilDate != null) {
				pstmt.setTimestamp(3, new Timestamp(utilDate.getTime()));
			} else {
				pstmt.setNull(3, Types.TIMESTAMP);
			}

			pstmt.setDouble(4, employee.getSalary());
			rowsAffected=pstmt.executeUpdate();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return rowsAffected;
	}

	public List<EmployeeBean> getEmployeeList() {
		List<EmployeeBean> employee = new ArrayList<>();
		String sql = "SELECT * from Employee";
		try (Connection conn = getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				EmployeeBean emp = new EmployeeBean();
				emp.setEmployeeId(rs.getInt("employeeId"));
				emp.setEmployeeName(rs.getString("employeeName"));
				emp.setInsertTime(rs.getTimestamp("insertTime"));
				emp.setRole(rs.getString("role"));
				emp.setSalary(rs.getDouble("salary"));
				employee.add(emp);

			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return employee;

	}

	public EmployeeBean getEmployeeById(int employeeId) {

		EmployeeBean emp = null;

		String sql = "SELECT * from Employee where employeeId=?";

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, employeeId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					emp = new EmployeeBean();
					emp.setEmployeeId(rs.getInt("employeeId"));
					emp.setEmployeeName(rs.getString("employeeName"));
					emp.setRole(rs.getString("role"));
					emp.setInsertTime(rs.getTimestamp("insertTime"));
					emp.setSalary(rs.getDouble("salary"));
				}
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return emp;

	}

	public void updateEmployee(EmployeeBean employee) {
		String sql = "update employee set employeeName=?,role=?,insertTime=?,salary=?,where employeeId=?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, employee.getEmployeeName());
			pstmt.setString(2, employee.getRole());
			// pstmt.setTimestamp(3, employee.getInsertTime());
			Date utilDate = employee.getInsertTime();
			if (utilDate != null) {
				pstmt.setTimestamp(3, new Timestamp(utilDate.getTime()));
			} else {
				pstmt.setNull(3, Types.TIMESTAMP);
			}

			pstmt.setDouble(4, employee.getSalary());
			pstmt.setInt(5, employee.getEmployeeId());
			pstmt.executeUpdate();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteEmployee(int employeeId) {
		// TODO Auto-generated method stub
		String sql = "Delete from employee where employeeId=?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, employeeId);
			pstmt.executeUpdate();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();

		}

	}
}