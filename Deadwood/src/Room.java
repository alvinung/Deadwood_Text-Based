// Author: Alvin Ung

import java.util.*;

/* Actract Class Room:
 * Responsible for handling all room information
 * Displays all Neighboring rooms of each room and Abstractly implements showing
 * room info
 */
public abstract class Room {
    private ArrayList<Room> neighbors;
    private String name;

    /*============================= Constructors =============================*/
    
    public Room (String n) {
    	name = n;
    	neighbors = new ArrayList<Room>();
    }
    /*============================ Getters/Setters ============================*/
    
    public String getName() {
    	return name;
    }

    public void setNeighbors(Room n1, Room n2, Room n3){
        neighbors.add(n1);
        neighbors.add(n2);
        neighbors.add(n3);
    }

    public void setNeighbors(Room n1, Room n2, Room n3, Room n4){
        neighbors.add(n1);
        neighbors.add(n2);
        neighbors.add(n3);
        neighbors.add(n4);
    }
    
    public Room getNeighbor(int n){
        return neighbors.get(n);
    }

    /*============================= Public Methods =============================*/
    
    /* allNeighbors
     * Postconditions:
     * - print all neighbors of this room
     */
    public void allNeighbors(){
    	PublicRelations.print("Neighbors: ");
    	for(Room r : neighbors){
    		PublicRelations.print(r.getName());
    	}
    }

    /* isNeighbor
     * Preconditions:
     * - wanted room to check 
     * Postconditions:
     * - boolean if wanted room is a neighbor of this room
     */
    public boolean isNeighbor(Room r){
        return neighbors.contains(r);
    }
    
    /*============================= Abstract Methods =============================*/
    public abstract void showInfo();
}
