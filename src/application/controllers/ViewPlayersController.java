package application.controllers;

import application.Main;
import application.database.Database;
import application.database.sql.SqlPlayer;
import application.logic.Comparison;
import application.logic.Player;
import application.logic.PlayerSearch;
import application.logic.Position;
import application.logic.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Facilitates user's ability to query players' table and see results
 * of those queries via the ViewPlayers.fxml page
 * 
 * @author nicholasgliserman
 */
public class ViewPlayersController {
	private String mostRecentSearch;
	
	// SEARCH FIELDS
	@FXML
	protected TextField firstNameSearch;
	@FXML
	protected TextField lastNameSearch;
	@FXML
	protected ComboBox<Team> teamSearch;
	@FXML
	protected ComboBox<Position> positionSearch;
	@FXML
	protected ComboBox<Comparison> comparisonOperator;
	@FXML
	protected TextField numberSeasonsPlayed;
	// RESULTS TABLE
	@FXML
	protected TableView<Player> playersTable;
	@FXML
	protected TableColumn<Player, String> firstNameCol;
	@FXML
	protected TableColumn<Player, String> lastNameCol;
	@FXML
	protected TableColumn<Player, String> positionCol;
	@FXML
	protected TableColumn<Player, String> teamCol;
	@FXML
	protected TableColumn<Player, String> numSeasonsCol;
	MultipleSelectionModel<Player> msm;
	
	/**
	 * Populates the dropdown menus and the TableView for
	 * when the fxml file first loads.
	 */
	@FXML
	protected void initialize()
	{
		// SEARCH FIELDS
		// INITIALIZE COMPARISON CHOICES for NUMBER SEASONS PLAYED		
		this.comparisonOperator.setItems(Comparison.getComparisons());
		this.comparisonOperator.setValue(Comparison.getComparison("Equals"));
		
		// INITIALIZE POSITIONS
		ObservableList<Position> tempPositions = FXCollections.observableArrayList();
		tempPositions.add(null);
		tempPositions.addAll(Position.getPositions());
		this.positionSearch.setItems(tempPositions);
		
		// INITIALIZE TEAMS
		ObservableList<Team> tempTeam = FXCollections.observableArrayList();
		tempTeam.add(null);
		tempTeam.addAll(Team.getTeams());
		this.teamSearch.setItems(tempTeam);
		
		// POPULATE TABLE
		searchAll();
	}
	
	// SEARCH OPERATIONS
	/**
	 * Takes input parameters and passes to database
	 * classes to show results.
	 */
	@FXML
	protected void search()
	{
		PlayerSearch ps = new PlayerSearch(firstNameSearch, lastNameSearch, positionSearch, teamSearch, comparisonOperator, numberSeasonsPlayed);
		this.mostRecentSearch = SqlPlayer.selectWhere(ps);
		Database db = new Database();
		ObservableList<Player> players = db.executeSelectPlayer(this.mostRecentSearch);
		showPlayers(players);
	}
	
	/**
	 * Shows user all the players currently in the database.
	 */	
	@FXML
	protected void searchAll()
	{
		this.mostRecentSearch = SqlPlayer.selectAll();
		Database db = new Database();
		ObservableList<Player> players = db.executeSelectPlayer(this.mostRecentSearch);
		showPlayers(players);
	}
	
	/**
	 * Repeats the most recent select query.
	 * 
	 * When a player is added / updated / deleted, this method
	 * refreshes the TableView to reflect changes to the player table. 
	 */
	protected void runMostRecentSearch()
	{
		Database db = new Database();
		ObservableList<Player> players = db.executeSelectPlayer(this.mostRecentSearch);
		showPlayers(players);
	}
	
	/**
	 * Places a list of Players within the TableView.
	 * 
	 * @param players 
	 */
	public void showPlayers(ObservableList<Player> players)
	{	
		// ADD RECORDS
		this.playersTable.setItems(players);
		
		// SET COLUMN VALUES for the RECORDS
		this.firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		this.lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		this.positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
		this.teamCol.setCellValueFactory(new PropertyValueFactory<>("team"));
		this.numSeasonsCol.setCellValueFactory(new PropertyValueFactory<>("numSeasonsPlayed"));
		
		// SELECTION MODEL
        this.msm = this.playersTable.getSelectionModel();
        this.msm.selectedItemProperty();
	}
	
	
	// INSERT / UPDATE / DELETE OPERATIONS
	/**
	 * Opens a new form window where user can input
	 * values to make a new player.
	 */
	@FXML
	protected void add()
	{
		Player player = new Player();
		addEditPlayer(player);
	}
	
	/**
	 * Opens a new form window, prefilled with details
	 * of the selected player.
	 */
	@FXML
	protected void edit()
	{
		// GET SELECTED PLAYER
		Player player = this.msm.getSelectedItem();
		addEditPlayer(player);
	}
	
	private void addEditPlayer(Player player)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("views/ViewAddEditPlayer.fxml"));
			VBox vbox = loader.load();
			AddEditPlayerController aepController = loader.<AddEditPlayerController>getController();
			aepController.initialize();
			aepController.setPlayer(player);
			Scene scene = new Scene(vbox);
			Stage secondaryWindow = new Stage();
			aepController.setStage(secondaryWindow);
			secondaryWindow.setScene(scene);
			secondaryWindow.showAndWait();
			runMostRecentSearch();
		}
		catch (Exception e) {e.printStackTrace();}
	}
	
	/**
	 * Deletes selected player from database.
	 */
	@FXML
	protected void remove()
	{
		try 
		{
			Player player = this.msm.getSelectedItem();
			Database db = new Database();
			db.execute(SqlPlayer.deletePlayer(player));
			runMostRecentSearch();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}
