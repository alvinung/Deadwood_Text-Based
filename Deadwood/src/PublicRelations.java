// Author: Alvin Ung

import java.util.Scanner;
import java.util.HashMap;

/* Class PublicRelations:
 * Responsible for handling all interactions with the user
 * Handles user input data and printing all data to the user
 */
public class PublicRelations {
    
	/*============================= Public Methods =============================*/
	
    /* print
     * Postconditions:
     * - print string
     */
    public static void print(String s){
    	System.out.println(s);
    }
    
    /*============================ Getters/Setters ============================*/
    public static String getInput(){
    	Scanner in = new Scanner (System.in);
		String input = in.nextLine();
		return input;
    }
    
    /* who
     * Preconditions:
     * - current Actor
     * Postconditions:
     * - print actor's information
     */
    public static void who(Actor a){
    	
    	System.out.println(a.getId() +"(" + "$" + a.checkWallet() + ", " + a.viewCredits() + "cr" + ")");
    	if(a.getRole() == null) {
    		System.out.println("Role: None");
    	} else{
    		Role role = a.getRole();
    		System.out.println("Role: " + role.getTitle() + ", \"" + role.getLine() + "\"");
    		System.out.println("Practice Chips: " + a.getRole().getRehearsals());
    	}
    }

    /* where
     * Preconditions:
     * - current Actor
     * Postconditions:
     * - print where the current actor's location information
     */
    public static void where(Actor a){
    	String room = a.getPosition().getName();
    	if (room == "Trailers" || room == "Casting Office"){
    		System.out.println(room);
    	} else{
    		System.out.println(room);
    		if ( ((Stage)a.getPosition()).getShotCounter() == 0) {
    			System.out.println("Scene Wrapped!");
    		} else{
    			System.out.println("Scene: " + ((Stage)a.getPosition()).getScene().getTitle() + " (Budget: " + ((Stage) a.getPosition()).getScene().getBudget() + "M)");
    		}
    	}
    }

    /* printBoard
     * Postconditions:
     * - print each room and information of each scene
     */
    public static void printBoard(){
    	System.out.println("Board State:");
        HashMap<String, Room> allRooms =  Deadwood.getBoard().getAllRooms();
        int numRooms = Deadwood.getBoard().getAllRooms().size();

        System.out.format("%20s%20s\n","Rooms:", "Scenes:");
        Object[][] table = new String[numRooms][];
        for(int i = 0; i < numRooms; i++){
            table[i] = new String[2];
        }

        int c1 = 0;
        for(String key: allRooms.keySet()){
            table[c1][0] = key;
            c1 = c1 + 1;
        }

        int c2 = 0;
        for(Room room: allRooms.values()){
            if (room.getName().equals("Casting Office") || room.getName().equals("Trailers")){
                table[c2][1] = "No Scenes";
                c2 = c2 + 1;
            } else{
                boolean sceneFlipped = ((Stage) room).getSceneFlipped();
                int shotCounter = ((Stage) room).getShotCounter();
                if (shotCounter == 0) {
                	table[c2][1] = "Completed";
                    c2 = c2 + 1;
                } else if (sceneFlipped == false){
                    table[c2][1] = "Hidden";
                    c2 = c2 + 1;
                } else{
                    table[c2][1] = "Available";
                    c2 = c2 + 1;
                }
            }
        }

        for (Object[] row : table) {
            System.out.format("%20s%20s\n", row);
        }
    }
    
}
