package com.aca.rest.dao;

import java.util.ArrayList;
import java.util.List;

import com.aca.rest.model.Movie;

public class MovieDaoMock implements MovieDao
{
	private static List<Movie> movies = new ArrayList<Movie>();
	
//	This is static because we want it to persist throughout several instantiations of the MovieDaoMock class.
	static
	{
		movies.add(new Movie("The World Is Not Enough", "Action", 1981));
		movies.add(new Movie("Star Trek", "SyFy", 1982));
		movies.add(new Movie("The Jerk", "Comedy", 1983));
		movies.add(new Movie("XXX","Action", 1984));
		movies.add(new Movie("The Hills Have Pies", "Horror", 1985));
	}
	
	public MovieDaoMock()
	{
		System.out.println("MovieDaoMock Instantiated");
	}
	
	public List<Movie> getAllMovies(Integer minReleaseYear)
	{
		List<Movie> myMovies = new ArrayList<Movie>();
		for(Movie movie : MovieDaoMock.movies)
		{
			if(movie.getReleaseYear() >= minReleaseYear)
			{
				myMovies.add(movie);
			}
		}
		return myMovies;
	}

	public List<Movie> getByGenre(String genre) 
	{
		List<Movie> myMovies = new ArrayList<Movie>();
		
		for(Movie movie : MovieDaoMock.movies)
		{
			if(movie.getGenre().equalsIgnoreCase(genre))
			{
				myMovies.add(movie);
			}
		}
		return myMovies;
	}

	public List<Movie> getByReleaseYear(Integer year) 
	{
		List<Movie> myMovies = new ArrayList<Movie>();
		
		for(Movie movie : MovieDaoMock.movies)
		{
			if(movie.getReleaseYear() == year)
			{
				myMovies.add(movie);
			}
		}
		return myMovies;
	}

	public List<Movie> getByMovieId(int movieId) 
	{
		List<Movie> myMovies = new ArrayList<Movie>();
		
		for(Movie movie : MovieDaoMock.movies)
		{
			if(movie.getMovieId() == movieId)
			{
				myMovies.add(movie);
			}
		}
		return myMovies;
	}

	public List<Movie> deleteByMovieId(int movieId) 
	{
		List<Movie> deletedMovies = new ArrayList<Movie>();
		
		for(Movie movie : MovieDaoMock.movies)
		{
			if(movie.getMovieId() == movieId)
			{
				deletedMovies.add(movie);
				MovieDaoMock.movies.remove(movie);
				break;
			}
		}
		return deletedMovies;
	}

	@Override
	public Movie addMovie(Movie newMovie) 
	{
		newMovie.setMovieId(getNewMovieId());
		MovieDaoMock.movies.add(newMovie);
		return newMovie;
	}
	
	private int getNewMovieId()
	{
		int maxMovieId = 0;
		for(Movie movie : MovieDaoMock.movies)
		{
			if(movie.getMovieId() > maxMovieId)
			{
				maxMovieId = movie.getMovieId();
			}
		}
		return maxMovieId + 1;
	}

	@Override
	public Movie updateMovie(Movie updateMovie) 
	{
		Movie myMovie = null;
		for(Movie movie : MovieDaoMock.movies)
		{
			if(movie.getMovieId() == updateMovie.getMovieId())
			{
				if(updateMovie.getTitle() != null)
				{
					movie.setTitle(updateMovie.getTitle());
//					movie.setLastUpdated();
				}
				if(updateMovie.getGenre() != null)
				{
					movie.setGenre(updateMovie.getGenre());
//					movie.setLastUpdated();
				}
				if(updateMovie.getReleaseYear() != null)
				{
					movie.setReleaseYear(updateMovie.getReleaseYear());
//					movie.setLastUpdated();
				}
				
				myMovie = movie;
				break;
			}
		}
		
		return myMovie;
	}

	@Override
	public int getCount() 
	{
		return MovieDaoMock.movies.size();
	}

	@Override
	public Movie getMostPopular() 
	{
		Movie mostPopular = null;
		if(!MovieDaoMock.movies.isEmpty())
		{
			mostPopular = MovieDaoMock.movies.get(0);
		}
		return mostPopular;
	}

	@Override
	public void publish(String subject, String message) 
	{
		AwsSnsPublish publish = new AwsSnsPublish();
		publish.publishMessage(message, subject);
		
	}
}
