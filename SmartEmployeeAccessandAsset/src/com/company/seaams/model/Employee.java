package com.company.seaams.model;

public class Employee  extends Person{
	private String email;
	private double salary;
	private String department;
	private String status;
	private String country;
	{
		this.country="INDIA";
		this.status="ACTIVE";
		
	}
	public Employee() {
		super();
	}
	
	public Employee(int id, String name, String email, double salary,String department) {
		super(id,name);
		this.email=email;
		this.salary=salary;
		this.department=department;
	}
	public String getEmail() {
		return email;
	}
	public String getDepartment() {
		return department;
	}
	public double getSalary() {
		return salary;
	}
	public String getStatus() {
		return status;
	}
	public String getCountry() {
		return country;
	}
	
	
	public boolean equals(Object obj) {
		if(this==obj)return true;
		if(obj==null||this.getClass()!=obj.getClass()) return false;
		Employee emp=(Employee)obj;
		return this.id==emp.id;
	}
	@Override
	public int hashCode() {
		return this.id;
	}
	public String toString() {
		return String.format("EmpID:%-6d|NAME:%-10s|Email:%-22s|Dept:%-8s|Salary:$%-9.2f|[%s-%s]",id,name,email,department,salary,status,country);
	}
	
}
