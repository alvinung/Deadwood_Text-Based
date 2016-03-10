// Author: Alvin Ung

import java.util.ArrayList;

/* Class Scene:
 * Responsible for handling Scene (card) information
 * Pays star actors and displays Scene information
 */
public class Scene implements Payment{

	private String title;
	private String description;
	private int budget;
	private ArrayList<Role> starRoles;

	/*============================= Constructors =============================*/
	
	public Scene(String t, String d, int b, ArrayList<Role> roles) {
		title = t;
		description = d;
		budget = b;
		starRoles = roles;
	}

	/*============================ Getters/Setters ============================*/
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public int getBudget() {
		return budget;
	}
	
	public void setBudget(int budget) {
		this.budget = budget;
	}

	public ArrayList<Role> getStarRoles() {
		return starRoles;
	}
	
	public void setStarRoles(Role role) {
		starRoles.add(role);
	}

	/*============================= Public Methods =============================*/

	/* payActor
	 * Preconditions:
	 * - Actor has a Star Role and has 'acted'
	 * Postconditions:
	 * - If successful, actor is paid 2 credits
	 * - If unsuccessful, nothing happens
	 */
	public void payActor(String outcome, Actor a) {
		if (outcome == "success") {
			a.setCredits(a.viewCredits() + 2);
			PublicRelations.print(a.getId() + " was paid " + 2 + " credits");
		} else {
			PublicRelations.print("Job Failed.");
		}
	}
	
	/* showInfo
	 * Preconditions:
	 * - flipped indicates whether the scene is visible
	 * Postconditions:
	 * - Scene info is printed
	 */
	public void showInfo(boolean flipped) {
		String taken;
		if (flipped == true) {
			PublicRelations.print(title + " ($" + budget + "M)\n" + description);
			PublicRelations.print("Star Roles:");
			for (Role r : starRoles) {
				if (r.getActor() != null) {
					taken = "taken";
				} else {
					taken = "";
				}
				PublicRelations.print("    " + r.getTitle() + " \"" + r.getLine() + "\" " + r.getRank() + " " + taken);
			}
		} else {
			PublicRelations.print("Scene Hidden");
		}
	}
	
}
