// Author: Alvin Ung

/* Class Trailer:
 * Responsible for handling information on the Trailer room
 * Displays information on which rooms connect to the Trailer
 */
public class Trailer extends Room{
	
	private String description;
	
	/*============================= Constructors =============================*/
	
	public Trailer(String n){
		super(n);
	}

	/*============================ Getters/Setters ============================*/
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	 /*============================= Public Methods =============================*/
	
	/* showInfo
	 * Preconditions:
	 * - Actor has input 'info' command
	 * Postconditions:
	 * - Neighboring room info is printed
	 */
	public void showInfo() {
		allNeighbors();
	}
}
