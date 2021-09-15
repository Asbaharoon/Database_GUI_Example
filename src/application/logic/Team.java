package application.logic;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents an association of players based in a particular city 
 * who play together in the NBA.
 * 
 * @author nicholasgliserman
 */

public class Team {
	private static ObservableList<Team> teams = FXCollections.observableArrayList();
	private int id;
	private String name;
	private String city;
	private int gamesWon;
	
	/**
	 * Constructs a team with a unique id from parameters
	 * fetched from the database.
	 * 
	 * After setting the parameters, the new team is added to 
	 * the static ObservableList called teams, which is used
	 * to populate dropdown menus -- whether to add / edit / search
	 * for a player.
	 * 
	 * @param id		unique integer value associated with this team in the database
	 * @param name		String denoting what the team is called
	 * @param city		String containing the home city of the team
	 * @param gamesWon	integer value representing the number of games won in the current season
	 */
	public Team(int id, String name, String city, int gamesWon)
	{
		setID(id);
		setName(name);
		setCity(city);
		setGamesWon(gamesWon);
		addTeam(this);
	}

	/**
	 * Places each team created in a static list that can be accessed 
	 * by dropdown menus throughout the application.
	 * 
	 * @param team	Team object to be added to the static list.
	 */
	private void addTeam(Team team) {teams.add(team);}
	
	// SETTERS
	private void setGamesWon(int gamesWon) {this.gamesWon = gamesWon;}
	private void setName(String name) {this.name = name;}
	private void setCity(String city) {this.city = city;}
	private void setID(int id) {this.id = id;}

	// GETTERS
	public int getID() {return this.id;}
	public String getName() {return this.name;}
	public String getCity() {return this.city;}
	public int getGamesWon() {return this.gamesWon;}
	public static ObservableList<Team> getTeams() {return teams;}
	
	/**
	 * Searches all the teams with this ID.
	 * 
	 * @param 	teamID
	 * @return  the team associated with the ID parameter
	 */
	public static Team getTeam(int id) 
	{
		for (int i = 0; i < teams.size(); i++)
		{
			if (teams.get(i).getID() == id)
				return teams.get(i);
		}
		return null;
	}
	
	/**
	 * Searches all teams with this name.
	 * 
	 * @param   name	what this team is called.
	 * @return			a Team object with this name
	 */
	public static Team getTeam(String name)
	{
		for (int i = 0; i < teams.size(); i++)
		{
			if (teams.get(i).getName().equalsIgnoreCase(name))
				return teams.get(i);
		}
		return null;
	}
	
	@Override
	public String toString() {return getName();}
}
