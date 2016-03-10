// Author: Alvin Ung

/* Class Role:
 * Responsible for handliing what actors are able to do when they have a role.
 * Implements acting and rehearsing
 */
public class Role {
	private String title;
	private String line;
	private int rank;
	private int rehearsals;
	private Actor actor;
	
	/*============================= Constructors =============================*/
	
	public Role (String t, String l)
	{
		title = t;
		line = l;
		rank = 1;
		rehearsals = 0;
		actor = null;
	}
	
	public Role (String t, String l, int rk)
	{
		title = t;
		line = l;
		rank = rk;
		rehearsals = 0;
		actor = null;
	}
	
	/*============================ Getters/Setters ============================*/
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String t)
	{
		
		title = t;
	}
	
	public int getRank()
	{
		return rank;
	}
	
	public void setRank(int r)
	{
		rank = r;
	}
	
	public String getLine()
	{
		return line;
	}
	
	public void setLine(String l)
	{
		line = l;
	}
	
	public int getRehearsals()
	{
		return rehearsals;
	}
	
	public void setRehearsals(int r)
	{
		rehearsals = r;
	}
	
	public Actor getActor()
	{
		return actor;
	}
	
	public void setActor(Actor a)
	{
		actor = a;
	}
	
	/*============================= Public Methods =============================*/
	
	/* rehearse
	 * Preconditions:
	 * - Actor has input 'rehearse' command
	 * Postconditions:
	 * - Actor gains 1 practice chip
	 */
	public boolean rehearse(boolean didSomething)
	{
		int max = ((Stage) actor.getPosition()).getScene().getBudget() - 1;
		if (didSomething == true) {
			PublicRelations.print("You've already done something. Please end your turn.");
		} else {
			if (!actor.getPosition().getName().equals("Casting Office") && 
					!actor.getPosition().getName().equals("Trailers")) {
				if (rehearsals == max) {
					PublicRelations.print("You cannot rehearse anymore.");
				} else {
					rehearsals += 1;
					PublicRelations.print(actor.getId() + " gained 1 practice chip!");
					didSomething = true;
				}
			} else {
				PublicRelations.print("You cannot rehearse here.");
			}
		}
		return didSomething;
	}
	
	/* act
	 * Preconditions:
	 * - Actor has input 'act' command
	 * Postconditions:
	 * - Actor rolls a die. Die value is added to practice chip count and compared
	 *   Scene budget
	 * - If greater than Scene budget, job is successful
	 */
	public boolean act(Scene scene, Stage stage, boolean didSomething)
	{
		if (didSomething == true){
			PublicRelations.print("You've already done something. Please end your turn.");
			return true;
		} else {
			int diceRoll = Deadwood.diceRoll();
			
			if ((diceRoll + rehearsals) >= scene.getBudget()) {
				if (actor.getAmStar() == true) {
					PublicRelations.print("You rolled a " + diceRoll + " and had " + rehearsals + " practice chips!");
					scene.payActor("success", actor);
					stage.setShotCounter(stage.getShotCounter() - 1);
					PublicRelations.print("Job is successful! 1 shot counter has been removed.");
				} else {
					PublicRelations.print("You rolled " + diceRoll + " and had " + rehearsals + " practice chips!");
					stage.payActor("success", actor);
					stage.setShotCounter(stage.getShotCounter() - 1);
					PublicRelations.print("Job is successful! 1 shot counter has been removed.");
				}
			}
			else {
				if (actor.getAmStar() == false) {
					stage.payActor("failed",actor);
					PublicRelations.print("You rolled " + diceRoll + " and had " + rehearsals + " practice chips!");
					PublicRelations.print("Job failed.");
				} else {
					PublicRelations.print("You rolled " + diceRoll + " and had " + rehearsals + " practice chips!");
					PublicRelations.print("Job failed.");
				}
			}
			didSomething = true;
		}
		return didSomething;
	}
	
}
