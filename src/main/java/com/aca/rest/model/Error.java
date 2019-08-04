package com.aca.rest.model;

public class Error 
{
	private int number;
	private String message;
	
	public Error(int number, String message)
	{
		this.number = number;
		this.message = message;
	}

	public int getNumber() 
	{
		return number;
	}

	public String getMessage() 
	{
		return message;
	}
	
}
