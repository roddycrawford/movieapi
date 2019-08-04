package com.aca.rest.model;

public class MovieSummary 
{
	public int totalMovies;
	public Movie mostPopular;
	
	public int getTotalMovies() 
	{
		return totalMovies;
	}
	public Movie getMostPopular() 
	{
		return mostPopular;
	}
	public void setTotalMovies(int totalMovies) 
	{
		this.totalMovies = totalMovies;
	}
	public void setMostPopular(Movie mostPopular) 
	{
		this.mostPopular = mostPopular;
	}
}
