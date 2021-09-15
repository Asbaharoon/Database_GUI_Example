package application.controllers;

import application.database.Database;
import application.database.sql.SqlPlayer;
import application.logic.Player;
import application.logic.Position;
import application.logic.Team;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Facilites user input via the ViewAddEditPlayer.fxml page.
 * 
 * @author nicholasgliserman
 */
public class AddEditPlayerController 
{
	private Player player;
	private Stage stage;
	@FXML
	protected Label heading;
	@FXML
	protected TextField firstName;
	@FXML
	protected TextField lastName;
	@FXML
	protected TextField numSeasonsPlayed;
	@FXML
	protected ComboBox<Position> position;
	@FXML
	protected ComboBox<Team> team;
	
	/*
	 * Adds items to the dropdown options.
	 */
	protected void initialize()
	{
		// INITIALIZE POSITIONS
		this.position.setItems(Position.getPositions());
		
		// INITIALIZE TEAMS
		this.team.setItems(Team.getTeams());
	}
	
	/**
	 * Takes user input and, if valid, saves it in the database.
	 * 
	 * Validates field values and then sets them in the Player object. 
	 * If the Player Object has a positive id, calls the database
	 * to perform an update; else an insert. Finally, closes this form
	 * window.
	 * 
	 * TODO: ALERT BOX or SOME METHOD of REPORTING ERROR to USER
	 */
	@FXML
	protected void save()
	{
		try {
			// VALIDATE FIELDS
			if (firstName.getText().isBlank())
				throw new Exception("Must Include First Name");
			if (lastName.getText().isBlank())
				throw new Exception("Must Include Last Name");
			if (numSeasonsPlayed.getText().isBlank())
				throw new Exception("Must Include Number of Seasons Played");
			int tempNumSeasons = Integer.parseInt(numSeasonsPlayed.getText());
			if (tempNumSeasons < 0)
				throw new Exception("Number of Seasons Played Must be Greater Than or Equal to Zero");
			if (position.getValue() == null)
				throw new Exception("Must Choose a Position");
			if (team.getValue() == null)
				throw new Exception("Must Choose a Team");
			
			// SET PLAYER VALUES
			this.player.setFirstName(firstName.getText());
			this.player.setLastName(lastName.getText());
			this.player.setNumSeasonsPlayed(tempNumSeasons);
			this.player.setPosition(position.getValue());
			this.player.setTeam(team.getValue());
		
			// DATABASE
			Database db = new Database();
			
			if (this.player.getPlayerID() <= 0) // PLAYER NOT IN DATABASE, INSERT
				db.execute(SqlPlayer.insertPlayer(this.player));
			else // PLAYER ALREADY IN DATABASE, UPDATE
				db.execute(SqlPlayer.update(this.player));
			this.stage.close();
		}
		catch (NumberFormatException nfe)
		{
			System.out.println("Number of Seasons Must be a Whole Number");
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
	// SETTERS
	/**
	 * Depending on whether the player is a new one to be created
	 * or an existing one to be edited, determines the header text and
	 * fills in values for the fields
	 * 
	 * @param  newPlayer	true if "new player" or false if "edit player"
	 */
	protected void setFieldValues()
	{
		if (this.player.getPlayerID() > 0)
		{
			this.heading.setText("Edit Player");
			this.firstName.setText(this.player.getFirstName());
			this.lastName.setText(this.player.getLastName());
			this.numSeasonsPlayed.setText(Integer.toString(this.player.getNumSeasonsPlayed()));
			this.position.setValue(this.player.getPosition());
			this.team.setValue(this.player.getTeam());
		}
		else
		{
			this.heading.setText("New Player");
		}
	}
	
	/**
	 * Uses a Player object to initialize the field values.
	 * 
	 * @param  player	Player object which may or may not exist in the database
	 */
	public void setPlayer(Player player)
	{
		this.player = player;
		setFieldValues();
	}
	
	/**
	 * Sets the stage in which this form appears so that 
	 * a successful save will close the stage.
	 * 
	 * @param stage
	 */
	public void setStage(Stage stage) {this.stage = stage;}
}
