package com.aca.rest.service;

import java.util.List;

import com.aca.rest.dao.EmployeeDao;
import com.aca.rest.model.Employee;

public class EmployeeService 
{
	EmployeeDao dao = new EmployeeDao();
	
	public List<Employee> getAllEmployees() 
	{
		return dao.getAllEmployees();
	}

	public Employee addEmployee(Employee newEmployee) 
	{
		int rowCount = dao.insertEmployees(newEmployee);
		System.out.println("number of employee inserts: " + rowCount);
		return newEmployee;
	}

}
