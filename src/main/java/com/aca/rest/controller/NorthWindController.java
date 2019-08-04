package com.aca.rest.controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aca.rest.model.Employee;
import com.aca.rest.service.EmployeeService;

@Path("/northwind")
public class NorthWindController 
{
	@GET
	@Path("/employees")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Employee> getAllEmployees()
	{
		EmployeeService service = new EmployeeService();
		return service.getAllEmployees();
	}
	
	@POST
	@Path("/employees")
	@Produces({MediaType.APPLICATION_JSON})
	public Employee addEmployee(Employee newEmployee)
	{
		EmployeeService service = new EmployeeService();
		return service.addEmployee(newEmployee);
	}
	
}
