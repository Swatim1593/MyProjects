package com.company.seaams.model;


public class ValidationUtil {
	public static Integer parseEmployeeId(String value) 
		throws NumberFormatException{
			return Integer.valueOf(value.trim());
		}
	public static Double parseSalary(String value)throws NumberFormatException{
		return Double.valueOf(value.trim());
	}

}
