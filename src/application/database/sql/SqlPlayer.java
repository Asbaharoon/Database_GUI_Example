package application.database.sql;

import java.util.ArrayList;

import application.logic.Player;
import application.logic.PlayerSearch;

public class SqlPlayer 
{	
	// QUERIES PERTAINING to WHOLE TABLES
	/**
	 * Generates SQL statement to create the players table
	 * 
	 * @return	String containing the CREATE statement
	 */
	public static String createTable()
	{
		return "CREATE TABLE players ( "
				+ "player_id int NOT NULL primary key "
				+ "GENERATED ALWAYS AS IDENTITY "
				+ "(START WITH 1, INCREMENT BY 1), "
				+ "team int NOT NULL, "
				+ "first_name varchar(45) DEFAULT NULL, "
				+ "last_name varchar(45) DEFAULT NULL, "
				+ "position int NOT NULL, "
				+ "num_seasons_played int)";
	}
	
	/**
	 * Generates SQL statement to drop (i.e. delete) the players table
	 * 
	 * @return	String containing the DROP statement
	 */
	public static String dropTable()
	{
		return "DROP TABLE players";
	}
	
	// INSERT, UPDATE, and DELETE STATEMENTS
	/**
	 * Generates an insert statement to add a single player.
	 * 
	 * @param player	Player object to be added to the database.
	 * @return			String containing the insert statement.
	 */
	public static String insertPlayer(Player player)
	{
		return insertIntoPlayersStatement() + "VALUES " + insertValueStatement(player);
	}
	
	private static String insertIntoPlayersStatement()
	{
		return "INSERT INTO players (team, first_name, last_name, position, num_seasons_played) ";
	}
	
	private static String insertValueStatement(Player player)
	{
		return "(" + player.getTeam().getID() + ", '"
		+ player.getFirstName() + "', '" + player.getLastName() + "', "
		+ player.getPosition().getPositionID() + ", " + player.getNumSeasonsPlayed()
		+ ")";
	}
	
	/**
	 * Generates an insert statement to add multiple players at once.
	 * 
	 * Currently unused method but could be used to do a mass upload.
	 * 
	 * @param players	ArrayList of Player objects to be inserted into the database
	 * @return			String containing the full insert statement.
	 */
	public static String insertPlayers(ArrayList<Player> players)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(insertIntoPlayersStatement() + "VALUES ");
		for (int i = 0; i < players.size(); i++)
		{
			if (i + 1 == players.size())
				sb.append(insertPlayer(players.get(i)));
			else
				sb.append(insertPlayer(players.get(i)) + ", ");
		}
		
		return sb.toString();
	}
	
	/**
	 * Generates a sql statement to delete a player with a specific id.
	 * 
	 * @param player 	Player object used to obtain the specific ID for the WHERE clause
	 * @return			a String contianing a DELETE FROM statement
	 */
	public static String deletePlayer(Player player) throws Exception
	{
		if (player.getPlayerID() <= 0) 
			throw new Exception("This Player is NOT in the Database");
		return "DELETE FROM players WHERE (player_id = " + player.getPlayerID() + ")";
	}

	/**
	 * Generates a sql statement to update a player already in the database.
	 * 
	 * @param player	Player object storing updated values
	 * @return 			a String containing the UPDATE statement.
	 */
	public static String update(Player player) throws Exception
	{
		if (player.getPlayerID() <= 0) 
			throw new Exception("This Player is NOT in the Database");
		return "UPDATE players SET "
				+ "team = " + player.getTeam().getID() + ", "
				+ "first_name = '" + player.getFirstName() + "', "
				+ "last_name = '" + player.getLastName() + "', "
				+ "position = " + player.getPosition().getPositionID() + ", "
				+ "num_seasons_played = " + player.getNumSeasonsPlayed() + " "
				+ "WHERE (player_id = " + player.getPlayerID() + ")";
	}
	
	// SELECT STATEMENTS
	/**
	 * Provides a sql statement to retrieve all records
	 * in the players table.
	 * 
	 * @return 	String with a SELECT ALL statement
	 */
	public static String selectAll()
	{
		return "SELECT * FROM players";
	}
	/**
	 * Dynamically builds a SELECT WHERE statement based
	 * on the criteria provided in the PlayerSearch object.
	 * 
	 * @param  ps 	PlayerSearch object with the conditions for the SELECT statement
	 * @return		String containing a SELECT WHERE statement
	 */
	public static String selectWhere(PlayerSearch ps)
	{
		int numConditions = ps.getNumberConditions();
		StringBuilder sb = new StringBuilder();
		sb.append(selectAll());
		
		if (numConditions > 0)
		{
			sb.append(" WHERE");
		
			if (ps.getFirstName() != null)
			{
				sb.append(" LOWER(first_name) LIKE '%" + ps.getFirstName().toLowerCase() + "%'");
				
				sb = addAnd(sb, --numConditions);
			}
			if (ps.getLastName() != null)
			{
				sb.append(" LOWER(last_name) LIKE '%" + ps.getLastName().toLowerCase() + "%'");
				sb = addAnd(sb, --numConditions);
			}
			if (ps.getTeam() != null)
			{
				sb.append(" team = " + ps.getTeam().getID());
				sb = addAnd(sb, --numConditions);
			}
			if (ps.getPosition() != null)
			{
				sb.append(" position = " + ps.getPosition().getPositionID());
				sb = addAnd(sb, --numConditions);
			}
			if (ps.getNumSeasonsPlayed() >= 0)
			{
				sb.append(" num_seasons_played " + ps.getComparisonOperator().getSqlCode() + " " + ps.getNumSeasonsPlayed());	
			}
		}
		return sb.toString();
	}
	
	private static StringBuilder addAnd(StringBuilder sb, int numConditions)
	{
		if (numConditions > 0)
			sb.append(" AND");
		return sb;
	}
} 
