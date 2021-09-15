package application;

import application.database.Database;
import application.database.sql.SqlPlayer;
import application.database.sql.SqlPosition;
import application.database.sql.SqlTeam;
import application.logic.Comparison;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Launches the application.
 * 
 * @author nicholasgliserman
 *
 */
public class Main extends Application
{
	/**
	 * Loads dropdown classes from database and launches
	 * the main application window.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		// BUILD COMPARISON OBJECTS
		Comparison.initializeComparisons();
		// RETRIEVE POSITIONS
		Database db = new Database();
		db.selectAllPositions();
		// RETRIEVE TEAMS
		db.selectAllTeams();
		
		// LOAD FXML FILE
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("views/ViewPlayers.fxml"));
		VBox vbox = loader.load();
		
		// LAUNCH THE WINDOW
		Scene scene = new Scene(vbox);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {launch(args);}
	
	/**
	 * Drops, creates, and fills the position_lkp table.
	 * 
	 * Use only when necessary to rebuild table from scratch.
	 * 
	 */
	private void createPositionTable()
	{
		Database db = new Database();
		db.execute(SqlPosition.dropTable());
		db.execute(SqlPosition.createTable());
		db.execute(SqlPosition.insertPositions());
	}
	
	/**
	 * Drops, creates, and fills the teams table.
	 * 
	 * Use only when necessary to rebuild table from scratch.
	 */
	private void createTeamTable()
	{
		Database db = new Database();
		db.execute(SqlTeam.dropTable());
		db.execute(SqlTeam.createTable());
		db.execute(SqlTeam.insertTeams());
	}
	
	/**
	 * Drops, creates, and fills the players table.
	 * 
	 * Use only when necessary to rebuild table from scratch.
	 */
	private void createPlayerTable()
	{
		Database db = new Database();
		db.execute(SqlPlayer.dropTable());
		db.execute(SqlPlayer.createTable());
	}
	
}
