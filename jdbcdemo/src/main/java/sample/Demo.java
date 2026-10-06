package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;



public class Demo {
	public static void main(String[] args) throws Exception,IOException{
		Connection conn=null;
		Statement stmt=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded successfully");
		}catch(ClassNotFoundException e) {
			System.out.println("Driver not loaded successfully");
			
		}
		try {
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcdemos","root","Swati@1234567890");
			System.out.println("Connection established successfully");
			 stmt=conn.createStatement();
			/*String cs= "CREATE TABLE EMP(Empid int, Empname char(20),Empsal int)";
			stmt.execute(cs);
			stmt.close();
			String cs= "INSERT INTO EMP VALUES(1001,'Shobha',45000)";
			stmt.executeUpdate(cs);
			//stmt=conn.createStatement();
			cs ="INSERT INTO EMP VALUES(1002,'Sheela',55000)";
			stmt.executeUpdate(cs);
			cs ="INSERT INTO EMP VALUES(1003,'Shaila',65000)";
			stmt.executeUpdate(cs);
			stmt.close();*/
			String cs ="select * from Emp";
			ResultSet rs =stmt.executeQuery(cs);
			ResultSetMetaData rsmd=rs.getMetaData();
			int cols=rsmd.getColumnCount();
			while(rs.next()) {
				for(int i=1;i<cols;i++) {
					String s=rs.getString(i);
					System.out.println(s+"");
				}
				System.out.println();
			}
			conn.setAutoCommit(false);
			cs="insert into Emp values(1004,'Rushil',70000)";
			stmt.execute(cs);
			BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter an Emp id to delete");
			int r = Integer.parseInt(br.readLine());
			
			String cs1 = "delete from Emp where Empid="+r;
			int status = stmt.executeUpdate(cs1);
			conn.commit();
			conn.setAutoCommit(true);
			if(status==0) {
				System.out.println("Record does not exist");
			}
			else {
				System.out.println(status+"records deleted");
			}
	

			stmt.close();
		} catch(SQLException s) {
		System.out.println("Connection not established successfully ");


}}}