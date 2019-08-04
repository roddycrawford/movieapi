package com.aca.rest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.aca.rest.model.Movie;

public class MovieDaoPrepStatementDemo implements MovieDao
{
	public MovieDaoPrepStatementDemo()
	{
		System.out.println("MovieDaoPrepStatementDemo Instantiated");
	}
	
	private static String sqlInsertMovie = 
			" INSERT INTO movie (Title, ReleaseYear, GenreId) " +
			" VALUES (?, ?, ?)";

	@Override
	public Movie addMovie(Movie newMovie) 
	{		
		int	rowCount = 0;
		PreparedStatement statement = null;
		
		Connection conn = MariaDbUtil.getConnection();

		try 
		{				
			statement = conn.prepareStatement(sqlInsertMovie);
			statement.setString(1, newMovie.getTitle());
			statement.setInt(2, newMovie.getReleaseYear());
			statement.setString(3, newMovie.getGenre());
			
			rowCount = statement.executeUpdate();				
				
			newMovie.setMovieId(getLastMovieId(conn));
			System.out.println("Rows effected: " + rowCount);
			
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
		
		return newMovie;
	}

	private Integer getLastMovieId(Connection conn) throws SQLException 
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
	
	
	@Override
	public List<Movie> getAllMovies(Integer minReleaseYear) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Movie> getByGenre(String genre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Movie> getByReleaseYear(Integer year) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Movie> getByMovieId(int movieId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Movie> deleteByMovieId(int movieId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Movie updateMovie(Movie updateMovie) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Movie getMostPopular() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void publish(String subject, String message) {
		// TODO Auto-generated method stub
		
	}

}
