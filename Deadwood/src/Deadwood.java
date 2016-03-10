// Author: Alvin Ung

import java.util.*;
import java.util.Random;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


/* Class Deadwood:
 * Main class - Responsible for handing the flow of the game
 */
public class Deadwood {
	private static int day;
	private static int scenesCount;
	private static int whoseTurn = 0;
	private static Board gameBoard;

	/*============================ Getters/Setters ============================*/
	
	public static Board getBoard() {
		return gameBoard;
	}
	
	public static int getSceneCount() {
		return scenesCount;
	}
	
	public static void setSceneCount(int c) {
		scenesCount = c;
	}
	
	/*============================= Public Methods =============================*/
	
	public static int diceRoll() {
		Random r = new Random();
		int rolled = r.nextInt(6) + 1;

		return rolled;
	}

	/*============================= Helper Methods =============================*/
	
	/*
	 * readRoles 
	 * Preconditions: 
	 * - fn is a valid filename 
	 * - each line has the following format
	 * 	 	role title / line / rank 
	 * Postconditions: - returns an array of roles
	 */
	private static ArrayList<Role> readRoles(String fn) {
		ArrayList<Role> roles = new ArrayList<Role>();
		String title = null;
		String line = null;
		int rank = 0;

		try {
			File f = new File(fn);
			Scanner scanner = new Scanner(f);

			// Read in each role and add it to array
			while (scanner.hasNextLine()) {
				Scanner s = new Scanner(scanner.nextLine());
				s.useDelimiter(" / ");
				title = s.next();
				line = s.next();
				rank = Integer.parseInt(s.next());
				Role r = new Role(title, line, rank);
				roles.add(r);
				s.close();
			}

			scanner.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return roles;
	}
	
	/*
	 * createScenes Preconditions: - The file containing scenes is in the
	 * following format: num_star_roles / title / description / budget
	 * Postconditions: - returns an array of scenes
	 */
	private static ArrayList<Scene> createScenes(ArrayList<Role> stars) {
		ArrayList<Scene> scenes = new ArrayList<Scene>();
		int numRoles = 0;
		String title = null;
		String description = null;
		int budget = 0;
		int ix = 0;

		try {
			File f = new File("../scenes.txt");
			Scanner scanner = new Scanner(f);

			// Read in each scene and add it to array
			while (scanner.hasNextLine()) {
				Scanner s = new Scanner(scanner.nextLine());
				s.useDelimiter(" / ");

				numRoles = Integer.parseInt(s.next());
				ArrayList<Role> sRoles = new ArrayList<Role>(stars.subList(ix, ix + numRoles));
				ix += numRoles;

				title = s.next();
				description = s.next();
				budget = Integer.parseInt(s.next());

				Scene sc = new Scene(title, description, budget, sRoles);
				scenes.add(sc);
				s.close();
			}

			scanner.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return scenes;
	}
	
	/* askNumPlayers
	 * Repeatedly asks for user input until valid number given
	 * Postconditions:
	 * - returns the number of players
	 */
	private static int askNumPlayers() {
		int numPlayers = 0;
		
		while (numPlayers == 0) {
			PublicRelations.print("How many players? (2 or 3)");
			try {
				numPlayers = Integer.parseInt(PublicRelations.getInput());
				if (numPlayers != 2 && numPlayers != 3) {
					PublicRelations.print("Must be eiher 2 or 3.");
					numPlayers = 0;
				}
			} catch (NumberFormatException e) {
				PublicRelations.print("Not a valid number (must be an integer).");
			}
		}
		
		return numPlayers;
	}
	
	/* nameActors
	 * Asks for each players name (id)
	 * Postconditions:
	 * - Returns an array containing the actor objects
	 */
	private static Actor[] nameActors (int num) {
		Actor[] aList = new Actor[num];
		String name = "default";
		
		for (int i = 0; i < num; i++) {
			PublicRelations.print("Player " + (i+1) + ", please enter your desired name:");
			name = PublicRelations.getInput();
			Actor temp = new Actor(name, gameBoard.getRoom("trailers"));
			aList[i] = temp;
		}
		
		return aList;
	}
	
	/* scoreGame 
	 * Prints a score board 
	 */
	private static void scoreGame(Actor[] actors) {
		int score;

		PublicRelations.print("\nFinal Score \n==========================================================");
		PublicRelations.print("||              Player | Money | Credits | Rank | Score || "
				+ "\n||---------------------|-------|---------|------|-------||");
		for (Actor a : actors) {
			score = 0;
			score += a.getRank() * 6 + a.checkWallet() + a.viewCredits();
			PublicRelations.print(String.format("||%20s |%6d |%8d |%5d |%6d ||", 
					a.getId(), a.checkWallet(), a.viewCredits(), a.getRank(), score));
		}
		PublicRelations.print("==========================================================");
	}
	
	/*================================= MAIN =================================*/
	
	public static void main(String[] args) {
		ArrayList<Role> extraRoles;
		ArrayList<Role> starRoles;
		ArrayList<Scene> scenes;

		extraRoles = readRoles("../extraRoles.txt");
		starRoles = readRoles("../starRoles.txt");

		scenes = createScenes(starRoles);
		Collections.shuffle(scenes);
		
		gameBoard = new Board(extraRoles);

		int numPlayers = askNumPlayers();

		Actor[] actorList = nameActors(numPlayers);

		// Keeps track of used scenes
		int first = 0;
		int last = 10;

		for (day = 1; day <= 3; day++) {
			
			PublicRelations.print("\nSetting Scenes for New Day -- Day " + day);
			gameBoard.setScenes(scenes.subList(first, last));
			first = last;
			last += 10;
			
			gameBoard.resetShotCounters();
			
			// Move actors back to trailers
			for (Actor a : actorList) {
				a.setPosition(gameBoard.getRoom("trailers"));
			}
			
			scenesCount = 10;
			
			String commands = "Available Commands: \n who \n where \n move \n work \n upgrade \n rehearse \n act \n end \n info \n";
			while (scenesCount > 1) {
				
				boolean turnEnded = false;
				boolean didSomething = false;
				
				Actor currentActor = actorList[whoseTurn];
				PublicRelations.printBoard();
				PublicRelations.print("\n" + currentActor.getId() + "'s turn\n");
				PublicRelations.print(commands + "Type 'commands' to see this prompt again.\n");
				
				while (!turnEnded) {
					PublicRelations.print("\nEnter command:");
					String command = PublicRelations.getInput();
					
					if (command.equals("info")) {
						PublicRelations.print("Which room do you want information about?");
						String st = PublicRelations.getInput();
						Room request = gameBoard.getRoom(st.toLowerCase());
						if (request == null) {
							PublicRelations.print("Invalid room name.");
						} else {
							if (request.getName().equals("Trailers")) {
								((Trailer) request).showInfo();
							} else if (request.getName().equals("Casting Office")) {
								((CastingOffice) request).showInfo();
							} else {
								((Stage) request).showInfo();
							}
							
						}
						
					} else if (command.equals("commands")){
						PublicRelations.print(commands);
						
					} else if (command.equals("who")) {
						PublicRelations.who(currentActor);

					} else if (command.equals("where")) {
						PublicRelations.where(currentActor);

					} else if (command.equals("move")) {
						didSomething = currentActor.move(didSomething);

					} else if (command.equals("work")) {
						didSomething = currentActor.takeRole(didSomething);

					} else if (command.equals("upgrade")) {
						currentActor.raiseRank();

					} else if (command.equals("rehearse")) {
						if (currentActor.getRole() == null) {
							PublicRelations.print("Woops! You don't currently have a role to rehearse for.");
						} else {
							didSomething = currentActor.getRole().rehearse(didSomething);
						}

					} else if (command.equals("act")) {
						if (currentActor.getRole() == null) {
							PublicRelations.print("Woops! You don't currently have a role to act on.");
						} else {
							didSomething = currentActor.getRole().act(
									((Stage) currentActor.getPosition()).getScene(),
									((Stage) currentActor.getPosition()), didSomething);
						}

					} else if (command.equals("end")) {
						turnEnded = true;

					} else {
						PublicRelations.print("Invalid command. Try Again.");
					}
				}
				whoseTurn = (whoseTurn + 1) % numPlayers;
			}
		}
		scoreGame(actorList);
	}
}
