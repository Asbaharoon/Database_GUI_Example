package application.logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents the primary role that a player has 
 * on their team.
 * 
 * @author nicholasgliserman
 *
 */
public class Position 
{
	private static ObservableList<Position> positions = FXCollections.observableArrayList();
	private int positionID;
	private String name;
	
	public Position(int positionID, String name)
	{
		setName(name);
		setPositionID(positionID);
		positions.add(this);
	}
	
	// SETTERS
	public void setPositionID(int positionID) {this.positionID = positionID;}
	public void setName(String name) {this.name = name;}
	
	// GETTERS
	public int getPositionID() {return positionID;}
	public String getName() {return name;}
	public static ObservableList<Position> getPositions() {return positions;}
	
	/**
	 * Searches all the positions with this ID.
	 * 
	 * @param positionID
	 * @return  the position name associated with the positionID parameter
	 */
	public static Position getPosition(int id)
	{
		for (int i = 0; i < positions.size(); i++)
		{
			if (positions.get(i).getPositionID() == id)
				return positions.get(i);
		}
		return null;
	}
	
	/**
	 * Searches all positions with this name.
	 * 
	 * @param  name	String value representing what this position is called.
	 * @return 	a Position with this name
	 */
	public static Position getPosition(String positionName)
	{
		for (int i = 0; i < positions.size(); i++)
		{
			if (positions.get(i).getName().equalsIgnoreCase(positionName))
				return positions.get(i);
		}
		return null;
	}
	
	@Override
	public String toString() {return getName();}
}
