package application.logic;

import application.database.sql.SqlPlayer;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Contains all of the parameters inputted by the user to shape
 * the search of the player(s) they want to see.
 * 
 * @author nicholasgliserman
 */
public class PlayerSearch extends Player {
	private int numConditions = 0;
	private Comparison seasonsComparison;
	
	/**
	 * Extracts the raw user input from the javafx elements (e.g. TextFields, ComboBoxes)
	 * used to receive that input.
	 * 
	 * @param firstNameSearch		TextField containing desired first name
	 * @param lastNameSearch		TextField containing desired last name
	 * @param positionSearch		ComboBox with the desired Position
	 * @param teamSearch			ComboBox with the desired Team
	 * @param comparisonOperator	ComboBox with desired Comparison to be used in conjuction with number of seasons played
	 * @param numberSeasonsPlayed	TextField contianing the desired number of seasons played.
	 */
	public PlayerSearch(TextField firstNameSearch, TextField lastNameSearch, ComboBox<Position> positionSearch, 
			ComboBox<Team> teamSearch, ComboBox<Comparison> comparisonOperator, TextField numberSeasonsPlayed) {
		System.out.println("In Player Search");
		if (!firstNameSearch.getText().isBlank())
		{
			super.setFirstName(firstNameSearch.getText());
			this.numConditions++;
		}
		if (!lastNameSearch.getText().isBlank())
		{
			super.setLastName(lastNameSearch.getText());
			this.numConditions++;
		}
		if (positionSearch.getValue() != null)
		{
			super.setPosition(positionSearch.getValue());
			this.numConditions++;
		}
		if (teamSearch.getValue() != null)
		{
			super.setTeam(teamSearch.getValue());
			this.numConditions++;
		}
		setComparisonOperator(comparisonOperator.getValue());
		if (!numberSeasonsPlayed.getText().isBlank())
		{
			super.setNumSeasonsPlayed(Integer.parseInt(numberSeasonsPlayed.getText()));
			this.numConditions++;
		}
	}
	
	public PlayerSearch() {}
	
	// SETTERS
	public void setComparisonOperator(Comparison comparison) {this.seasonsComparison = comparison;}
	
	// GETTERS
	public Comparison getComparisonOperator() {return this.seasonsComparison;}
	public int getNumberConditions() {return this.numConditions;}
	
	@Override
	public void print() {System.out.println(super.toString() + " " + this.seasonsComparison.toString());}
	public void printSqlStatement() {System.out.println(SqlPlayer.selectWhere(this));}
	
}
