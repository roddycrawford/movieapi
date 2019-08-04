package com.aca.rest.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.xml.bind.DatatypeConverter;

import com.aca.rest.model.Employee;


public class EmployeeDao 
{
	private final static String EmployeeIdColumn = " EmployeeId ";
	
	private final static String allEmployeeColumns = 
			" LastName, FirstName, TitleId, TitleOfCourtesyId, " +
			" BirthDate, HireDate, Address, City, StateId, ZipCode, PersonalPhone, " +
			" Extension, Picture, Notes, ReportsToEmployeeId, Salary, HobbyId ";
	
	private final static String sqlSelectAllEmployees = 
			" SELECT " + allEmployeeColumns + " , " + EmployeeIdColumn +
			" FROM employee ";
	
	private final static String sqlInsertEmployee = 
			"  Insert INTO employee ( " + allEmployeeColumns +  " ) " +
			"  VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "; //17 columns - employeeId will be generated.
	
			
	
	public List<Employee> getAllEmployees() 
	{
		List<Employee> employees = new ArrayList<Employee>();
		
		ResultSet result = null;
		Statement statement = null;
		
		Connection conn = MariaDbUtil.getConnection();

		try 
		{			
			statement = conn.createStatement();			
			result = statement.executeQuery(sqlSelectAllEmployees);
			
			while(result.next()) 
			{
				Employee employee = makeEmployee(result);				
				employees.add(employee);
			}			
		} 
		catch (SQLException e) 
		{			
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{				
				result.close();
				statement.close();
				conn.close();
			} 
			catch (SQLException e) 
			{				
				e.printStackTrace();
			}
		}
		
		return employees;
	}

	private Employee makeEmployee(ResultSet result) throws SQLException 
	{
		Employee employee = new Employee();		//17 columns in employee table
		
		employee.setId(result.getInt("EmployeeID"));
		employee.setFirstName(result.getString("FirstName"));
		employee.setLastName(result.getString("LastName"));
		employee.setBirthDate(result.getObject("Birthdate", LocalDate.class));
		employee.setHireDate(result.getObject("Hiredate", LocalDate.class));
		employee.setAddress(result.getString("Address"));
		employee.setCity(result.getString("City"));
		employee.setStateId(result.getString("StateId"));
		employee.setSalary(result.getBigDecimal("Salary"));
		employee.setPersonalPhone(result.getString("PersonalPhone"));
		employee.setExtension(result.getString("Extension"));
		employee.setNotes(result.getString("notes"));
		employee.setReportsToEmployeeId(result.getInt("ReportsToEmployeeId"));		
		/* Base64 encoding schemes are commonly used when there is a need to encode binary data 
		 * that needs be stored and transferred over media that are designed to deal with textual data. 
		 * This is to ensure that the data remains intact without modification during transport. */
		byte[] picture = result.getBytes("Picture");
		String pictureString = DatatypeConverter.printBase64Binary(picture);
		employee.setPicture(pictureString);
		
		employee.setTitleId(result.getInt("TitleId"));
		employee.setTitleOfCourtesyId(result.getInt("TitleOfCourtesyId"));
		employee.setZipcode(result.getString("Zipcode"));
		employee.setHobbyId(result.getInt("HobbyId"));
		
		return employee;
	}
	
	public int insertEmployees(Employee newEmployee) 
	{		
		int	rowCount = 0;
		PreparedStatement statement = null;
		
		Connection conn = MariaDbUtil.getConnection();

		try 
		{				
			statement = conn.prepareStatement(sqlInsertEmployee);
			statement.setString(1, newEmployee.getLastName());
			statement.setString(2, newEmployee.getFirstName());
			statement.setInt(3, newEmployee.getTitleId());
			statement.setInt(4, newEmployee.getTitleOfCourtesyId());
			statement.setObject(5, newEmployee.getBirthDate());
			statement.setObject(6, newEmployee.getHireDate());
			statement.setString(7, newEmployee.getAddress());
			statement.setString(8, newEmployee.getCity());
			statement.setString(9, newEmployee.getStateId());
			statement.setString(10, newEmployee.getZipcode());
			statement.setString(11, newEmployee.getPersonalPhone());
			statement.setString(12, newEmployee.getExtension());			
			
			byte[] picture = DatatypeConverter.parseBase64Binary(newEmployee.getPicture());	
			Blob pictureBlob = new SerialBlob(picture);
			statement.setBlob(13, pictureBlob);			
			
			statement.setString(14, newEmployee.getNotes());
			
			if (newEmployee.getReportsToEmployeeId() != null) 
			{
				statement.setInt(15, newEmployee.getReportsToEmployeeId());
			} 
			else 
			{
				statement.setNull(15, java.sql.Types.INTEGER);
			}
			statement.setBigDecimal(16, newEmployee.getSalary());
			
			if (newEmployee.getHobbyId() != null) 
			{
				statement.setInt(17, newEmployee.getHobbyId());
			} 
			else 
			{
				statement.setNull(17, java.sql.Types.INTEGER);
			}
			
			rowCount = statement.executeUpdate();				
				
			newEmployee.setId(getLastKey(conn));		
			
		} 
		catch (SQLException e) 
		{			
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{				
				statement.close();
				conn.close();
			} 
			catch (SQLException e) 
			{				
				e.printStackTrace();
			}
		}
		
		return rowCount;		
	}
	
	private Integer getLastKey(Connection conn) throws SQLException 
	{
		Integer key = 0;
		Statement statement = conn.createStatement();			
		ResultSet result = statement.executeQuery(" SELECT LAST_INSERT_ID() ");
		
		while(result.next()) 
		{
			key = result.getInt("LAST_INSERT_ID()");				
		}
		return key;
	}
}
