package com.aca.rest.model;

import java.time.LocalDateTime;

public class Movie 
{
	private String title;
	private String genre;
	private Integer releaseYear;
	private int movieId;
	private static int lastKeyUsed = 100;
	private LocalDateTime createDateTime;
	private LocalDateTime lastUpdated;
	
//	private static int totalMovies;
	
	public Movie(String title, String genre, Integer releaseYear)
	{
		this.title = title;
		this.genre = genre;
		this.releaseYear = releaseYear;
//		totalMovies++;
		this.movieId = ++lastKeyUsed;
		this.createDateTime = LocalDateTime.now();
		this.lastUpdated = LocalDateTime.now();
	}
	
//	Adding this constructor because jersey needs to make the thing and then set the variables
	public Movie()
	{
		this.createDateTime = LocalDateTime.now();
		this.lastUpdated = getCreateDateTime();
//		this.key = ++lastKeyUsed;
	}

	public String getTitle() 
	{
		return title;
	}

	public String getGenre() 
	{
		return genre;
	}

	public Integer getReleaseYear() 
	{
		return releaseYear;
	}

	public int getMovieId() 
	{
		return movieId;
	}

	public LocalDateTime getCreateDateTime() 
	{
		return createDateTime;
	}

	public LocalDateTime getLastUpdated() 
	{
		return lastUpdated;
	}
	
	public void setTitle(String title) 
	{
		this.title = title;
	}

	public void setGenre(String genre) 
	{
		this.genre = genre;
	}

	public void setReleaseYear(Integer releaseYear) 
	{
		this.releaseYear = releaseYear;
	}

	public void setMovieId(int movieId) 
	{
		this.movieId = movieId;
	}

	public void setCreateDateTime(LocalDateTime createDateTime) 
	{
		this.createDateTime = createDateTime;
	}

	public void setLastUpdated(LocalDateTime lastUpdated) 
	{
		this.lastUpdated = lastUpdated;
	}

	@Override
	public String toString()
	{
		
		
		return "title: " + this.title;
	}

//	private int getNewKey()
//	{
//		return 0;
//	}
	
//	public static int getTotalMovies() 
//	{
//		return totalMovies;
//	}
	
	
}
