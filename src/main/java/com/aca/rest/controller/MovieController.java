package com.aca.rest.controller;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.aca.rest.model.EmailMessage;
import com.aca.rest.model.Message;
import com.aca.rest.model.Movie;
import com.aca.rest.model.MovieSummary;
import com.aca.rest.model.TextSNS;
import com.aca.rest.service.MovieService;

@Path("/movies")
public class MovieController 
{	
	@Inject
	private MovieService service;
	
//	MovieService service = new MovieService();
	
	@GET
	@Path("/summary")
	@Produces(MediaType.APPLICATION_JSON)
	public MovieSummary getSummary()
	{
		return service.getSummary();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> getAllMovies(@QueryParam("minReleaseYear") int minReleaseYear)
	{
		return service.getAllMovies(minReleaseYear);
	}
	
	@GET
	@Path("/genre/{value}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> getByGenre(@PathParam("value") String genre)
	{
		return service.getByGenre(genre);
	}
	
	@GET
	@Path("/releaseyear/{value}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> getByReleaseYear(@PathParam("value") int year)
	{
		return service.getByReleaseYear(year);
	}
	
	@GET
	@Path("/{movieId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> getByMovieId(@PathParam("movieId") int movieId)
	{
		return service.getByMovieId(movieId);
	}
	
	@GET
	@Path("/fancysearch")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> getByFancySearch(
			@QueryParam("english") String english,
			@QueryParam("french") String french,
			@QueryParam("german") String german,
			@QueryParam("spanish") String spanish,
			@QueryParam("media") String media,
			@QueryParam("startdate") String startdate,
			@QueryParam("enddate") String enddate) {
		System.out.println("english: " + english);
		System.out.println("french: " + french);
		System.out.println("german: " + german);
		System.out.println("spanish: " + spanish);
		System.out.println("media: " + media);
		System.out.println("startdate: " + startdate);
		System.out.println("enddate: " + enddate);
		
		return getAllMovies(0);
	}
	
	@DELETE
	@Path("/{value}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> deleteByMovieId(@PathParam("value") int movieId)
	{
		return service.deleteByMovieId(movieId);
	}
	
	@POST
	@Path("/email")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendEmail(EmailMessage emailMessage)
	{
		String result = service.sendEmail(emailMessage);
		
		Message message = new Message();
		message.setMessage(result);
		
		return Response.status(200).entity(message).build();
	}
	
	@POST
	@Path("/text")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendCustomerText(TextSNS textSNS)
	{
		String result = service.sendText(textSNS);
		
		Message message = new Message();
		message.setMessage(result);
		
		return Response.status(200).entity(message).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Movie addMovie(Movie newMovie)
	{
		return service.addMovie(newMovie);
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Movie updateMovie(Movie updateMovie)
	{
		return service.updateMovie(updateMovie);
	}
}
