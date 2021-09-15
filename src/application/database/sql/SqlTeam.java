package application.database.sql;

/**
 * Creates SQL statements related to the team table
 * to be executed by the Database class.
 * 
 * @author nicholasgliserman
 *
 */
public class SqlTeam 
{	
	/**
	 * Generates SQL statement to create the teams table
	 * 
	 * @return	String containing the CREATE statement
	 */
	public static String createTable()
	{
		return "CREATE TABLE teams ("
				+ "team_id int not null primary key "
				+ "GENERATED ALWAYS AS IDENTITY "
				+ "(START WITH 1, INCREMENT BY 1), "
				+ "team_name varchar(45) DEFAULT NULL, "
				+ "city varchar(45) DEFAULT NULL, "
				+ "games_won int)";
	}

	/**
	 * Generates SQL statement to drop (i.e. delete) the teams table
	 * 
	 * @return	String containing the DROP statement
	 */
	public static String dropTable()
	{
		return "DROP TABLE teams";
	}
	
	/**
	 * Generates SQL statement to insert five teams ino the teams table
	 * 
	 * @return	String containing the INSERT statement
	 */
	public static String insertTeams()
	{
		return "INSERT INTO teams (team_name, city, games_won) VALUES "
				+ "('Los Angeles Lakers','Los Angeles',78),"
				+ "('Utah Jazz','Salt Lake City',35),"
				+ "('Golden State Warriors','San Francisco',74),"
				+ "('Denver Nuggets','Denver',49),"
				+ "('Oklahoma City Thunder','Oklahoma City',20)";
	}
	
	/**
	 * Generates SQL statement to retrieve all the teams
	 * 
	 * @return	String containing the SELECT statement
	 */
	public static String selectAll()
	{
		return "SELECT * FROM teams";
	}
}
