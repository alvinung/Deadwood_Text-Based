// Author: Alvin Ung

import java.util.ArrayList;


/* Class Actor:
 * Responsible for handing all information on the actor
 * Implements taking a role, moving the actor, and raising rank
 */ 
public class Actor {
	
	private String id;
	private int rank;
	private int money;
	private int credits;
	private Room position;
	private Role role;
	private boolean amStar;
	
	/*============================= Constructors =============================*/
	
	public Actor(String name, Room start)
	{
		id = name;
		rank = 1;
		money = 0;
		credits = 0;
		position = start;
		role = null;
	}
	
	/*============================ Getters/Setters ============================*/
	
	public boolean getAmStar()
	{
		return amStar;
	}
	
	public String getId()
	{
		return id;
	}

	public void setId(String s)
	{
		id = s;
	}

	public int getRank()
	{
		return rank;
	}
	
	public void setRank(int r)
	{
		rank = r;
	}

	public int checkWallet()
	{	
		return money;
	}

	public void setMoney(int m)
	{
		money = m;
	}
	
	public int viewCredits()
	{
		return credits;
	}
	
	public void setCredits(int c)
	{
		credits = c;
	}
	
	public Room getPosition()
	{
		return position;
	}

	public void setPosition(Room r)
	{
		position = r;
	}

	public Role getRole()
	{
		return role;
	}
	
	public void setRole(Role r)
	{
		role = r;
	}
	
	/*============================= Public Methods =============================*/
	
	/* takeRole
	 * Preconditions:
	 * - actor has input 'work' command
	 * Postconditions:
	 * - Actor is assigned role, if valid command <argument> sequence
	 * - Notified of error otherwise
	 */
	public boolean takeRole(boolean didSomething) {
		
		ArrayList<Role> starRoles = new ArrayList<Role>();
		ArrayList<Role> extraRoles = new ArrayList<Role>();
		
		if (role != null){
			PublicRelations.print("You already have a role.");
			return didSomething;
		}
		
		if (position.getName() == "Trailers" || position.getName() == "Casting Office") {
			PublicRelations.print("You cannot work here.");
			return didSomething;
		}
		
		if (((Stage) position).getShotCounter() == 0) {
			PublicRelations.print("This scene has wrapped.");
			return didSomething;
		}
		
		Stage stage = (Stage) position;
		Scene scene = ((Stage) position).getScene();
		
		// Available star roles
		starRoles = scene.getStarRoles();
		PublicRelations.print("Available Star Roles: ");
		for (Role r : starRoles) {
			if (r.getActor() == null) {
				PublicRelations.print(r.getTitle() + ", rank needed: " + r.getRank());
			}
		}
		// Available extra roles
		extraRoles = stage.getExtraRoles();
		PublicRelations.print("\nAvailable Extra Roles: ");
		for (Role r : extraRoles) {
			if (r.getActor() == null) {
				PublicRelations.print(r.getTitle() + ", rank needed: " + r.getRank());
			}
		}
		
		Role validRole = null;
		String desiredRole = PublicRelations.getInput();
		for (Role r : starRoles) {
			if (r.getTitle().equals(desiredRole)) {
				validRole = r;
				amStar = true;
			}
		}
		
		for (Role r : extraRoles) {
			if (r.getTitle().equals(desiredRole)) {
				validRole = r;
				amStar = false;
			}
		}
		
		if (validRole == null){
			PublicRelations.print("Not a valid role title. Please enter a role EXACTLY as it appears.");
		} else {
			if (validRole.getRank() > rank){
				PublicRelations.print("You do not have high enough rank.");
			} else {
				if (validRole.getActor() != null) {
					PublicRelations.print("That role is taken.");
				} else {
					role = validRole;
					validRole.setActor(this);
					PublicRelations.print("You have taken the role: " + role.getTitle());
					if (amStar == true) {
						((Stage) position).setStarCounter(((Stage) position).getStarCounter() + 1);
					}
					return true;
				}
			}
		}
		return didSomething;
	}
	
	/* move
	 * Preconditions:
	 * - Actor has given 'move' command
	 * Postconditions:
	 * - Actor has moved or been notified of error
	 */
	public boolean move(boolean didSomething)
	{
		if (role != null){
			PublicRelations.print("Working on role, cannot move.");
			return didSomething;
		} else{
			if (didSomething == true){
				PublicRelations.print("Can't move anymore.");
				return didSomething;
			} else{
				position.allNeighbors();
				String wantedRoom = PublicRelations.getInput();
				Room r = Deadwood.getBoard().getRoom(wantedRoom.toLowerCase());
				if(position.isNeighbor(r)){
					position = r;
					PublicRelations.print(id + " successfully moved to " + r.getName());
					if (!position.getName().equals("Casting Office") && !position.getName().equals("Trailers")) {
						((Stage) position).setSceneFlipped(true);
					}
					return true;
				} else{
					PublicRelations.print("Please enter valid room.");
					return didSomething;
				}
			}
		}
	}
	
	/* raiseRank
	 * Preconditions:
	 * - actor has given 'upgrade' command
	 * Postconditions:
	 * - Actor's rank has been increased to requested value
	 * - or been notified or error
	 */
	public void raiseRank()
	{
		int rnk = 0;
		
		if (!position.getName().equals("Casting Office")) {
			PublicRelations.print("You are not in the Casting Office. Cannot upgrade.");
		} else {
			CastingOffice co = ((CastingOffice)Deadwood.getBoard().getRoom("casting office"));
			co.getRankCosts();
			PublicRelations.print("How would you like to pay? ('$' or 'cr')");
			String type = PublicRelations.getInput();
			if (!type.equals("$") && !type.equals("cr")) {
				PublicRelations.print("Invalid payment type.");
			} else {
				PublicRelations.print("Which rank?");
				try {
					rnk = Integer.parseInt(PublicRelations.getInput());
				} catch (NumberFormatException e) {
					PublicRelations.print("Not a valid rank (must be an integer). Try again.");
				}
				if (rnk > 1 && rnk < 7){
					co.requestRankUp(type, rnk, this);
				} else{
					PublicRelations.print("Please enter a valid rank");
				}
			}
		}
	}

}
