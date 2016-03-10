// Author: Alvin Ung

/* Class CastingOffice
 * Responsible for handing all information on the Casting Office
 * Implements checks on raising rank, displaying rank costs and neighboring rooms
 */

public class CastingOffice extends Room{
    private final int[] dollars;
    private final int[] credits;
    
    /*============================= Constructors =============================*/
    
    public CastingOffice(String n){
        super(n);

        this.dollars = new int[7];
        dollars[2] = 4;
        dollars[3] = 10;
        dollars[4] = 18;
        dollars[5] = 28;
        dollars[6] = 40;
        
        this.credits = new int[7];
        credits[2] = 5;
        credits[3] = 10;
        credits[4] = 15;
        credits[5] = 20;
        credits[6] = 25;
    }
    
    /*============================ Getters/Setters ============================*/
    
    public void getRankCosts(){
        Object[][] table = new String[6][];
        table[0] = new String[] { "", "Dollars", "Credits" };
        table[1] = new String[] { "Rank 2", "4", "5" };
        table[2] = new String[] { "Rank 3", "10", "10" };
        table[3] = new String[] { "Rank 4", "18", "15" };
        table[4] = new String[] { "Rank 5", "28", "20" };
        table[5] = new String[] { "Rank 6", "40", "25" };
        
        StringBuilder rankCosts = new StringBuilder();
        for (Object[] row : table){
            String s = String.format("%15s%15s%15s\n", row);
            rankCosts.append(s);
        }
        PublicRelations.print(rankCosts.toString());
    }
    
    /*============================= Public Methods =============================*/
    
    public void requestRankUp(String type, int requestedRank, Actor anActor){
        if(type.equals("$")) {
            if(dollars[requestedRank] <= anActor.checkWallet()) {
                anActor.setRank(requestedRank);
                anActor.setMoney(anActor.checkWallet() - dollars[requestedRank]);

                PublicRelations.print("Actor has ranked up to " + requestedRank);
                PublicRelations.print("You have " + anActor.checkWallet() + " dollars left");
            }
            else{
                PublicRelations.print("Insufficient amount of dollars");
            }
        }
        else{
            if(credits[requestedRank] <= anActor.checkWallet()){
                anActor.setRank(requestedRank);
                anActor.setMoney(anActor.checkWallet() - credits[requestedRank]);

                PublicRelations.print("Actor has ranked up to " + requestedRank);
                PublicRelations.print("You have " + anActor.viewCredits() + " credits left");
            }
            else{
                PublicRelations.print("Insufficient amount of credits");
            }
        }
    }
    
    public void showInfo() {
    	getRankCosts();
    	PublicRelations.print(" ");
    	allNeighbors();
    }
   
}
