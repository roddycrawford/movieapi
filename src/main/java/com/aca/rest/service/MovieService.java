package com.aca.rest.service;

import com.aca.rest.model.EmailMessage;
import com.aca.rest.model.Error;

import java.time.LocalDateTime;
//import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.aca.rest.dao.AwsSnsPublish;
import com.aca.rest.dao.MovieDao;
import com.aca.rest.dao.MovieDaoImpl;
import com.aca.rest.dao.MovieDaoMock;
import com.aca.rest.model.Movie;
import com.aca.rest.model.MovieSummary;
import com.aca.rest.model.TextSNS;
import com.amazonaws.services.sns.AmazonSNSClient;

public class MovieService 
{
//	private MovieDao dao = new MovieDaoMock();
//	private MovieDao dao = new MovieDaoImpl();
	
	@Inject
	public MovieDao dao;
	
	public List<Movie> getAllMovies(int minReleaseYear)
	{
		return dao.getAllMovies(minReleaseYear);
	}

	public List<Movie> getByGenre(String genre) 
	{
		validateGenre(genre);
		return dao.getByGenre(genre);
	}

	public List<Movie> getByReleaseYear(int year) 
	{
		return dao.getByReleaseYear(year);
	}

	public List<Movie> getByMovieId(int movieId) 
	{
		validateMovieId(movieId);
		return dao.getByMovieId(movieId);
	}

	public List<Movie> deleteByMovieId(int movieId) 
	{
		validateMovieId(movieId);
		return dao.deleteByMovieId(movieId);
	}
	
	private boolean validateMovieId(int movieId)
	{
		boolean isValid = true;
		if((dao.getByMovieId(movieId).size() != 1) || (movieId == 0))
		{
			isValid = false;
			
			Error error = new Error(100, "invalid value for movieId, movie for \'" + movieId + "\' does not exist");
			Response response = Response.status(400)
					 .entity(error)
					 .build();
			throw new WebApplicationException(response);
		}
		
		return isValid;
	}
	
	private boolean validateGenre(String genre)
	{
		boolean isValid = false;
		if(genre.equalsIgnoreCase("action") ||
		   genre.equalsIgnoreCase("syfy") ||
		   genre.equalsIgnoreCase("comedy")||
		   genre.equalsIgnoreCase("horror"))
		{
			isValid = true;
		}
		else
		{
			Error error = new Error(101, "invalid value for genre: \'" + genre + "\'");
			Response response = Response.status(400)
					 .entity(error)
					 .build();
			throw new WebApplicationException(response);
		}
		
		return isValid;
	}
	
	private boolean validateReleaseYear(int releaseYear)
	{
		boolean isValid = false;
		int maxYear = LocalDateTime.now().getYear() + 2;
		if(releaseYear >= 1900 && releaseYear <= maxYear)
		{
			isValid = true;
		}
		else
		{
			Error error = new Error(102, "invalid value for Release Year: '" + releaseYear + "'");
			Response response = Response.status(400)
					 .entity(error)
					 .build();
			throw new WebApplicationException(response);
		}
		return isValid;
	}
	
	private boolean validateTitle(String title)
	{
		boolean isValid = false;
		if(null != title && title.length() > 2 && title.length() <= 50)
		{
			isValid = true;
		}
		else
		{
			Error error = new Error(103, "invalid value for Title: '" + title + "'");
			Response response = Response.status(400)
					 .entity(error)
					 .build();
			throw new WebApplicationException(response);
		}
		return isValid;
	}

	public Movie addMovie(Movie newMovie) 
	{
		validateGenre(newMovie.getGenre());
		validateReleaseYear(newMovie.getReleaseYear());
		validateTitle(newMovie.getTitle());
		
		Movie addedMovie = dao.addMovie(newMovie);
		
		sendNewMovieNotification(addedMovie);
		
		return addedMovie;
	}

	private void sendNewMovieNotification(Movie addedMovie) 
	{
		String subject = "New Movie Added: " + addedMovie.getTitle();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n");
		buffer.append("Title: " + addedMovie.getTitle() + "\n");
		buffer.append("Genre: " + addedMovie.getGenre() + "\n");
		buffer.append("Release Year: " + addedMovie.getReleaseYear());
		String message = buffer.toString();
		
		dao.publish(subject, message);
	}

	public Movie updateMovie(Movie updateMovie) 
	{
		boolean isValidUpdate = false;
		if(updateMovie.getGenre() != null)
		{
			validateGenre(updateMovie.getGenre());
			isValidUpdate = true;
		}
		if(updateMovie.getReleaseYear() != null)
		{
			validateReleaseYear(updateMovie.getReleaseYear());
			isValidUpdate = true;
		}
		if(updateMovie.getTitle() != null)
		{
			validateTitle(updateMovie.getTitle());
			isValidUpdate = true;
		}
		
		if(isValidUpdate)
		{
			validateMovieId(updateMovie.getMovieId());
		}
		else
		{
			invalidUpdate();
		}
		
		return dao.updateMovie(updateMovie);
	}

	private void invalidUpdateMovieId() 
	{
		Error error = new Error(106, "No Movie ID was entered, cannot update");
		Response response = Response.status(400)
				 .entity(error)
				 .build();
		throw new WebApplicationException(response);
	}

	private void invalidUpdate() 
	{
		Error error = new Error(105, "invalid update, no values to update");
		Response response = Response.status(400)
				 .entity(error)
				 .build();
		throw new WebApplicationException(response);
		
	}

	public MovieSummary getSummary() 
	{
		MovieSummary movieSummary = new MovieSummary();
		movieSummary.setTotalMovies(dao.getCount());
		movieSummary.setMostPopular(dao.getMostPopular());
		return movieSummary;
	}

	public String sendEmail(EmailMessage emailMessage) 
	{
		AwsSnsPublish service = new AwsSnsPublish();
		String messageId = service.sendEmail(emailMessage);
		return messageId;
	}

	public String sendText(TextSNS textSNS) 
	{
		AwsSnsPublish awsSnsService = new AwsSnsPublish();
		String messageId = awsSnsService.sendText(textSNS);
		return messageId;
	}
}
