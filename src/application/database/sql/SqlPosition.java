package application.database.sql;

public class SqlPosition {

	/**
	 * Generates SQL statement to drop (i.e. delete) the position_lkp table
	 * 
	 * @return	String containing the drop statement
	 */
	public static String dropTable()
	{
		return "DROP TABLE position_lkp";
	}
	
	/**
	 * Generates SQL statement to create the position_lkp table
	 * 
	 * @return	String containing the CREATE statement
	 */
	public static String createTable()
	{	
		return "CREATE TABLE position_lkp ( "
				+ "position_id int NOT NULL primary key "
				+ "GENERATED ALWAYS AS IDENTITY "
				+ "(START WITH 1, INCREMENT BY 1), "
				+ "position_name varchar(45) DEFAULT NULL)";
	}
	
	/**
	 * Generates SQL statement to insert five positions ino the position_lkp table
	 * 
	 * @return	String containing the INSERT statement
	 */
	public static String insertPositions()
	{
		return "INSERT INTO position_lkp (position_name) "
				+ "VALUES ('Point Guard'),"
				+ "('Shooting Guard'),"
				+ "('Small Forward'),"
				+ "('Power Forward'),"
				+ "('Center')";
	}
	
	/**
	 * Generates SQL statement to retrieve all the positions
	 * 
	 * @return	String containing the SELECT statement
	 */
	public static String selectAll()
	{
		return "SELECT * FROM position_lkp";
	}

}
