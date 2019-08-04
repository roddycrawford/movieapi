package com.aca.rest.dao;

import java.util.List;

import com.aca.rest.model.Movie;
import com.aca.rest.model.MovieSummary;

public interface MovieDao 
{
	public List<Movie> getAllMovies(Integer minReleaseYear);
	public List<Movie> getByGenre(String genre);
	public List<Movie> getByReleaseYear(Integer year);
	public List<Movie> getByMovieId(int movieId);
	public List<Movie> deleteByMovieId(int movieId);
	public Movie addMovie(Movie newMovie);
	public Movie updateMovie(Movie updateMovie);
	public int getCount();
	public Movie getMostPopular();
	public void publish(String subject, String message);
}
