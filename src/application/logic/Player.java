package application.logic;


/**
 * Represents a professional athlete in the NBA.
 * 
 * @author nicholasgliserman
 *
 */
public class Player {

	private int playerID = -1;
	private String firstName;
	private String lastName;
	private int numSeasonsPlayed = -1;
	private Team team;
	private Position position;
	
	/**
	 * Constructs an empty player to receive user values.
	 */
	public Player() {}
	
	/**
	 * Constructs a Player with data loaded from the database.
	 * 
	 * @param id				the unique integer value for this player in the database
	 * @param firstName			String representing the player's first name
	 * @param lastName			String representing the player's last name
	 * @param seasonsPlayed		the whole number of seasons played, which can be zero or greater
	 * @param team				the Team to which the player belongs
	 * @param position			the primary Position that the player plays in the game
	 */
	public Player(int id, String firstName, String lastName, int seasonsPlayed, Team team, Position position) 
	{
		buildPlayer(id, firstName, lastName, seasonsPlayed, team, position);
	}
	
	private void buildPlayer(int id, String firstName, String lastName, int seasonsPlayed, Team team, Position position)
	{
		setPlayerID(id);
		setFirstName(firstName);
		setLastName(lastName);
		setNumSeasonsPlayed(seasonsPlayed);
		setTeam(team);
		setPosition(position);
	}
	
	// SETTERS
	public void setPlayerID(int playerID) {this.playerID = playerID;}
	
	public void setFirstName(String firstName) 
	{
		if (firstName != null)
			this.firstName = firstName;
	}
	
	public void setLastName(String lastName) 
	{
		if (lastName != null)
			this.lastName = lastName;
	}
	
	public void setNumSeasonsPlayed(int numSeasonsPlayed) 
	{
		if (numSeasonsPlayed >= 0)
			this.numSeasonsPlayed = numSeasonsPlayed;
	}
	
	public void setTeam(Team team) 
	{
		if (team != null)
			this.team = team;
	}
	
	public void setPosition(Position position) 
	{
		if (position != null)
			this.position = position;
	}
	
	// GETTERS
	public int getPlayerID() {return this.playerID;}
	public String getFirstName() {return this.firstName;}
	public String getLastName() {return this.lastName;}
	public int getNumSeasonsPlayed() {return this.numSeasonsPlayed;}
	public Team getTeam() {return this.team;}
	public Position getPosition() {return this.position;}
	
	@Override
	public String toString() {return this.firstName + " " + this.lastName;}
	
	public void print() {System.out.println(this.toString());}

}
