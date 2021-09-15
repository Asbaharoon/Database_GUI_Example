package application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.database.sql.SqlPosition;
import application.database.sql.SqlTeam;
import application.logic.Player;
import application.logic.Position;
import application.logic.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Connects to the database and allows the application to
 * run general or specific query.
 * 
 * @author nicholasgliserman
 */
public class Database {
	private static String databaseURL = "jdbc:derby:Basketball;create=true";
	
	/**
	 * Performs select statement on the players table.
	 * 
	 * For each ResultSet item, builds a player object and adds to players collection.
	 * 
	 * @param  query	String containing a "SELECT...FROM players" statement
	 * @return			ObservableList<Player> that will populate a TableView
	 */
	public ObservableList<Player> executeSelectPlayer(String query)
	{
		ObservableList<Player> players = FXCollections.observableArrayList();
		
		try (Connection connection = DriverManager.getConnection(databaseURL);
				Statement statement = connection.createStatement())
		{
			ResultSet rs = statement.executeQuery(query);	
			while (rs.next())
			{
				// RETRIEVE ATTRIBUTES for PLAYER
				int id = rs.getInt("player_id");
				String fname = rs.getString("first_name");
				String lname = rs.getString("last_name");
				int numSeasons = rs.getInt("num_seasons_played");
				
				// RETRIEVE TEAM & POSITION IDs
				int teamID = rs.getInt("team");
				int posID = rs.getInt("position");
				
				//FIND TEAM & POSITION in MEMORY
				Team team = Team.getTeam(teamID);
				Position position = Position.getPosition(posID);
				
				// BUILD PLAYER and ADD to COLLECTION
				players.add(new Player(id, fname, lname, numSeasons, team, position)); // TEAM, POSITION
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return players;
	}
	
	/**
	 * Performs "SELECT * FROM positions_lkp table" and then loads
	 * positions into memory.
	 */
	public void selectAllPositions()
	{
		try (Connection connection = DriverManager.getConnection(databaseURL);
				Statement statement = connection.createStatement())
		{
			ResultSet rs = statement.executeQuery(SqlPosition.selectAll());	
			while (rs.next())
			{
				int id = rs.getInt("position_id");
				String name = rs.getString("position_name");
				Position newPos = new Position(id, name);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Performs "SELECT * FROM teams table" and then loads
	 * teams into memory.
	 */
	public void selectAllTeams()
	{
		try (Connection connection = DriverManager.getConnection(databaseURL);
				Statement statement = connection.createStatement())
		{
			ResultSet rs = statement.executeQuery(SqlTeam.selectAll());	
			while (rs.next())
			{
				int id = rs.getInt("team_id");
				String name = rs.getString("team_name");
				String city = rs.getString("city");
				int wins = rs.getInt("games_won");
				Team newTeam = new Team(id, name, city, wins);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Takes a sql statement and runs it in the database.
	 * 
	 * Used by the application to create & drop tables
	 * as well as insert and update into tables.
	 * 
	 * @param sqlStatement	query to be performed on the database.
	 */
	public void execute(String sqlStatement)
	{
		try (Connection connection = DriverManager.getConnection(databaseURL);
				Statement statement = connection.createStatement())
		{
			statement.execute(sqlStatement);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}
