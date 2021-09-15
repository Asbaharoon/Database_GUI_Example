package application.logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A utility class to populate dropdown menus, allowing users
 * to specificy a comparison operator.
 * 
 * The human-readable String (e.g. "greater than") is accompanied by 
 * the SQL equivalent (">") so a Comparison object can help to
 * dynamically generate a WHERE condition in a SQL clause.
 * 
 * @author nicholasgliserman
 */
public class Comparison {
	private static ObservableList<Comparison> comparisons = FXCollections.observableArrayList();;
	private String publicView;
	private String sqlCode;
	
	public Comparison(String publicView, String sqlCode)
	{
		setPublicView(publicView);
		setSqlCode(sqlCode);
	}
	
	/**
	 * Generates a static list of five Comparison objects.
	 */
	public static void initializeComparisons()
	{		
		comparisons.add(new Comparison("Greater Than", ">"));	
		comparisons.add(new Comparison("Greater Than or Equal", ">="));
		comparisons.add(new Comparison("Equals", "="));
		comparisons.add(new Comparison("Less Than", "<"));
		comparisons.add(new Comparison("Less Than or Equal", "<="));
	}

	// SETTERS
	private void setPublicView(String publicView) {this.publicView = publicView;}
	private void setSqlCode(String sqlCode) {this.sqlCode = sqlCode;}

	// GETTERS
	public String getPublicView() {return publicView;}
	public String getSqlCode() {return sqlCode;}
	public static ObservableList<Comparison> getComparisons() {return comparisons;}
	
	/**
	 * Locates the appropriate Comparison object matching the name
	 * inputted in the parameter
	 * 
	 * @param name	the String used to search the list of Comparisons
	 * @return		the Comparison object with a name matching the parameter
	 */
	public static Comparison getComparison(String name)
	{
		for (int i = 0; i < comparisons.size(); i++)
		{
			if (comparisons.get(i).getPublicView().equalsIgnoreCase(name))
				return comparisons.get(i);
		}
		return null;
	}
	
	@Override
	public String toString() {return getPublicView();}
}
