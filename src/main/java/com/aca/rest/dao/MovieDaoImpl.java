package com.aca.rest.dao;

//import java.sql.Blob;
import java.sql.Connection;
//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//import javax.sql.rowset.serial.SerialBlob;
//import javax.xml.bind.DatatypeConverter;

//import com.aca.rest.model.Employee;
import com.aca.rest.model.Movie;

public class MovieDaoImpl implements MovieDao
{
	private boolean useAWS = true;
	
	public MovieDaoImpl()
	{
		System.out.println("MovieDaoImpl Instantiated...");
	}
	
	private static String sqlGetAllMovies = 
			" SELECT MovieId, Title, ReleaseYear, " +
				   " GenreId, CreateDate, UpdateDate " +
			" FROM movie";
	
	private static String sqlInsertMovie = 
			" INSERT INTO movie (Title, ReleaseYear, GenreId) " +
			" VALUES (?, ?, ?) ";
	
	private static String sqlDeleteMovie =
			" DELETE FROM movie " +
			" WHERE MovieId = ? ";
	
	private static String sqlGetByMovieId = 
			" SELECT MovieId, Title, ReleaseYear, " +
					"GenreId, CreateDate, UpdateDate " +
			" FROM movie " +
			" WHERE movieId = ? ";
	
	private static String sqlGetByGenre =
			" SELECT MovieId, Title, ReleaseYear, " +
					"GenreId, CreateDate, UpdateDate " +
			" FROM movie " +
			" WHERE GenreId = ? ";
	
	private static String sqlGetByReleaseYear =
			" SELECT MovieId, Title, ReleaseYear, " +
					"GenreId, CreateDate, UpdateDate " +
			" FROM movie " +
			" WHERE ReleaseYear = ? ";
	
	private static String sqlGetCount = 
			" SELECT COUNT(MovieId) AS count " +
			" FROM movie ";	
	
	private static String sqlUpdateMovieByMovieId = 
			" UPDATE movie " +
			" SET Title = ?, " +
			"     GenreId = ?, " +
			"     ReleaseYear = ? " +
			" WHERE MovieId = ? ";
	
	private static String sqlGetMostPopular =
			" SELECT MovieId, Title, ReleaseYear, " +
					"GenreId, CreateDate, UpdateDate " +
			" FROM movie " +
			" WHERE MovieId = ( SELECT MAX(MovieId) FROM movie ) ";
	
	@Override
	public List<Movie> getAllMovies(Integer minReleaseYear) 
	{
		List<Movie> movies = new ArrayList<Movie>();
		
		ResultSet result = null;
		Statement statement = null;
		
		Connection conn = MariaDbUtil.getConnection();

		try 
		{			
			statement = conn.createStatement();			
			result = statement.executeQuery(sqlGetAllMovies);
			
			while(result.next()) 
			{
				if(result.getInt("releaseYear") >= minReleaseYear)
				{
					movies.add(makeMovie(result));
				}
			}			
		} 
		catch (SQLException e) 
		{			
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{				
				result.close();
				statement.close();
				conn.close();
			} 
			catch (SQLException e) 
			{				
				e.printStackTrace();
			}
		}
		
		return movies;
	}

	@Override
	public List<Movie> getByGenre(String genreId) 
	{		
		List<Movie> movies = new ArrayList<Movie>();
		
		ResultSet result = null;
		PreparedStatement statement = null;
		
		Connection conn = MariaDbUtil.getConnection();

		try 
		{
			statement = conn.prepareStatement(sqlGetByGenre);
			statement.setString(1, genreId);
			result = statement.executeQuery();				
				
			while(result.next()) 
			{
				movies.add(makeMovie(result));
			}
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
		return movies;
	}

	@Override
	public List<Movie> getByReleaseYear(Integer year)
	{		
		List<Movie> movies = new ArrayList<Movie>();
		
		ResultSet result = null;
		PreparedStatement statement = null;
		
		Connection conn = MariaDbUtil.getConnection();

		try 
		{
			statement = conn.prepareStatement(sqlGetByReleaseYear);
			statement.setInt(1, year);
			result = statement.executeQuery();				
				
			while(result.next()) 
			{
				movies.add(makeMovie(result));
			}
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
		return movies;
	}

	private Movie makeMovie(ResultSet result) throws SQLException
	{
		Movie movie = new Movie();	
		movie.setMovieId(result.getInt("movieId"));
		movie.setTitle(result.getString("title"));
		movie.setReleaseYear(result.getInt("releaseYear"));
		movie.setGenre(result.getString("genreId"));
		movie.setCreateDateTime(result.getObject("createDate", LocalDateTime.class));
		movie.setLastUpdated(result.getObject("updateDate", LocalDateTime.class));
		return movie;
	}
	
	@Override
	public List<Movie> getByMovieId(int movieId) 
	{		
		List<Movie> movies = new ArrayList<Movie>();
		
		ResultSet result = null;
		PreparedStatement statement = null;
		
		Connection conn = MariaDbUtil.getConnection();

		try 
		{
			statement = conn.prepareStatement(sqlGetByMovieId);
			statement.setInt(1, movieId);
			result = statement.executeQuery();				
				
			while(result.next()) 
			{
				movies.add(makeMovie(result));
			}
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
		
		return movies;
	}

	@Override
	public List<Movie> deleteByMovieId(int movieId)
	{	
		List<Movie> movies = this.getByMovieId(movieId);
		
		int	rowCount = 0;
		PreparedStatement statement = null;
		
		Connection conn = MariaDbUtil.getConnection();

		try 
		{				
			statement = conn.prepareStatement(sqlDeleteMovie);
			statement.setInt(1, movieId);
			rowCount = statement.executeUpdate();
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
		return movies;
	}

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
	public Movie updateMovie(Movie updateMovie) 
	{		
		int	rowCount = 0;
		PreparedStatement statement = null;
		
		Connection conn = MariaDbUtil.getConnection();

		try 
		{				
			statement = conn.prepareStatement(sqlUpdateMovieByMovieId);
			statement.setString(1, updateMovie.getTitle());
			statement.setString(2, updateMovie.getGenre());
			statement.setInt(3, updateMovie.getReleaseYear());
			statement.setInt(4, updateMovie.getMovieId());
			
			rowCount = statement.executeUpdate();				
			System.out.println("Row Updated: " + rowCount);
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
		
		return updateMovie;
	}

	@Override
	public int getCount() 
	{
		int count = 0;
		
		ResultSet result = null;
		Statement statement = null;
		
		Connection conn = MariaDbUtil.getConnection();

		try 
		{			
			statement = conn.createStatement();			
			result = statement.executeQuery(sqlGetCount);
			
			while(result.next()) 
			{
				count = result.getInt("count");
			}			
		} 
		catch (SQLException e) 
		{			
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{				
				result.close();
				statement.close();
				conn.close();
			} 
			catch (SQLException e) 
			{				
				e.printStackTrace();
			}
		}
		
		return count;
	}

	@Override
	public Movie getMostPopular() 
	{
		Movie mostPopular = null;
		
		ResultSet result = null;
		Statement statement = null;
		
		Connection conn = MariaDbUtil.getConnection();

		try 
		{			
			statement = conn.createStatement();			
			result = statement.executeQuery(sqlGetMostPopular);
			
			while(result.next()) 
			{
				mostPopular = makeMovie(result);
			}			
		} 
		catch (SQLException e) 
		{			
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{				
				result.close();
				statement.close();
				conn.close();
			} 
			catch (SQLException e) 
			{				
				e.printStackTrace();
			}
		}
		
		return mostPopular;
	}

	@Override
	public void publish(String subject, String message) 
	{
		if(useAWS)
		{
			AwsSnsPublish publish = new AwsSnsPublish();
			publish.publishMessage(message, subject);
		}
	}
}
