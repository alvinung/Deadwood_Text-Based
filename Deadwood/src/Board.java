// Author: Alvin Ung

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* Class Board:
 * Responsible for handling connecting rooms and setting up the board
 */

public class Board {
	
	private HashMap<String, Room> rooms = new HashMap<String, Room>();
	
	/*============================= Constructors =============================*/
	
	/* Board 
	 * Preconditions:
	 * - extras is in order for exact game recreation
	 * Postconditions:
	 * - Returns new instance of game-board
	 * - Exact replication of board in online pdf version
	 * Possible Extensions:
	 * - Randomize extra role assignment with method
	 *    that ensures fair rank distribution
	 * - Randomize assignment of neighbors
	 */
	public Board (ArrayList<Role> extras) {
		
		// Initialize Rooms
		rooms.put("trailers", new Trailer("Trailers"));
		rooms.put("casting office", new CastingOffice("Casting Office"));
		
		// Build Stages
		rooms.put("main street", new Stage("Main Street", 3));
		((Stage) rooms.get("main street")).setExtraRole(extras.get(0));
		((Stage) rooms.get("main street")).setExtraRole(extras.get(1));
		((Stage) rooms.get("main street")).setExtraRole(extras.get(2));
		((Stage) rooms.get("main street")).setExtraRole(extras.get(3));
		
		rooms.put("saloon", new Stage("Saloon", 2));
		((Stage) rooms.get("saloon")).setExtraRole(extras.get(4));
		((Stage) rooms.get("saloon")).setExtraRole(extras.get(5));
		
		rooms.put("ranch", new Stage("Ranch", 2));
		((Stage) rooms.get("ranch")).setExtraRole(extras.get(6));
		((Stage) rooms.get("ranch")).setExtraRole(extras.get(7));
		((Stage) rooms.get("ranch")).setExtraRole(extras.get(8));
		
		rooms.put("secret hideout", new Stage("Secret Hideout", 3));
		((Stage) rooms.get("secret hideout")).setExtraRole(extras.get(9));
		((Stage) rooms.get("secret hideout")).setExtraRole(extras.get(10));
		((Stage) rooms.get("secret hideout")).setExtraRole(extras.get(11));
		((Stage) rooms.get("secret hideout")).setExtraRole(extras.get(12));
		
		rooms.put("bank", new Stage("Bank", 1));
		((Stage) rooms.get("bank")).setExtraRole(extras.get(13));
		((Stage) rooms.get("bank")).setExtraRole(extras.get(14));
		
		rooms.put("church", new Stage("Church", 2));
		((Stage) rooms.get("church")).setExtraRole(extras.get(15));
		((Stage) rooms.get("church")).setExtraRole(extras.get(16));
		
		rooms.put("hotel", new Stage("Hotel", 3));
		((Stage) rooms.get("hotel")).setExtraRole(extras.get(17));
		((Stage) rooms.get("hotel")).setExtraRole(extras.get(18));
		((Stage) rooms.get("hotel")).setExtraRole(extras.get(19));
		((Stage) rooms.get("hotel")).setExtraRole(extras.get(20));
		
		rooms.put("jail", new Stage("Jail", 1));
		((Stage) rooms.get("jail")).setExtraRole(extras.get(21));
		((Stage) rooms.get("jail")).setExtraRole(extras.get(22));
		
		rooms.put("general store", new Stage("General Store", 2));
		((Stage) rooms.get("general store")).setExtraRole(extras.get(23));
		((Stage) rooms.get("general store")).setExtraRole(extras.get(24));
		
		rooms.put("train station", new Stage("Train Station", 3));
		((Stage) rooms.get("train station")).setExtraRole(extras.get(25));
		((Stage) rooms.get("train station")).setExtraRole(extras.get(26));
		((Stage) rooms.get("train station")).setExtraRole(extras.get(27));
		((Stage) rooms.get("train station")).setExtraRole(extras.get(28));
		
		// Set Neighbors
		rooms.get("trailers").setNeighbors(
				rooms.get("main street"),
				rooms.get("hotel"),
				rooms.get("saloon"));
		rooms.get("casting office").setNeighbors(
				rooms.get("train station"), 
				rooms.get("ranch"), 
				rooms.get("secret hideout"));
		rooms.get("main street").setNeighbors(
				rooms.get("trailers"), 
				rooms.get("jail"), 
				rooms.get("saloon"));
		rooms.get("saloon").setNeighbors(
				rooms.get("trailers"), 
				rooms.get("main street"), 
				rooms.get("general store"), 
				rooms.get("bank"));
		rooms.get("ranch").setNeighbors(
				rooms.get("casting office"), 
				rooms.get("general store"), 
				rooms.get("bank"), 
				rooms.get("secret hideout"));
		rooms.get("secret hideout").setNeighbors(
				rooms.get("casting office"), 
				rooms.get("ranch"), 
				rooms.get("church"));
		rooms.get("bank").setNeighbors(
				rooms.get("hotel"), 
				rooms.get("saloon"), 
				rooms.get("church"), 
				rooms.get("ranch"));
		rooms.get("church").setNeighbors(
				rooms.get("bank"), 
				rooms.get("hotel"), 
				rooms.get("secret hideout"));
		rooms.get("hotel").setNeighbors(
				rooms.get("trailers"), 
				rooms.get("bank"), 
				rooms.get("church"));
		rooms.get("jail").setNeighbors(
				rooms.get("train station"), 
				rooms.get("general store"), 
				rooms.get("main street"));
		rooms.get("general store").setNeighbors(
				rooms.get("train station"), 
				rooms.get("saloon"), 
				rooms.get("jail"), 
				rooms.get("ranch"));
		rooms.get("train station").setNeighbors(
				rooms.get("general store"), 
				rooms.get("casting office"), 
				rooms.get("jail"));
		
	}

	
	/*============================ Getters/Setters ============================*/
	
	/* getRoom
	 * Preconditions:
	 * - request is a a valid String
	 * Postconditions:
	 * - returns the requested room if name is valid, else null
	 */
	public Room getRoom (String request) {
		String roomName = request.toLowerCase();
		
		if (rooms.containsKey(roomName)) {
			return rooms.get(roomName);
		} else {
			return null;
		}
	}
	
	public HashMap<String, Room> getAllRooms() {
		return rooms;
	}
	
	/* setScenes
	 * Preconditions:
	 * - scenes is a randomized list of scenes of size NUM_STAGES
	 * Postconditions:
	 * - Each stage has been given a new scene
	 */
	public void setScenes (List<Scene> scenes) {
		int ix = 0;
		
		for (HashMap.Entry<String, Room> entry : rooms.entrySet()) {
			if (entry.getValue() instanceof Stage) {
				((Stage) rooms.get(entry.getKey())).setScene(scenes.get(ix));
				ix++;
			}
		}
	}
	
	/* resetShotCounters
	 * Preconditions:
	 * - It's the start of a new day
	 * Postconditions:
	 * - Every stage's shot counter has been reset to original
	 */
	public void resetShotCounters () {
		for (HashMap.Entry<String, Room> entry : rooms.entrySet()) {
			if (entry.getValue() instanceof Stage) {
				Stage s = ((Stage) rooms.get(entry.getKey()));
				s.setShotCounter(s.getOrigShotCounter());
				s.setSceneFlipped(false);
			}
		}
	}
	
}
