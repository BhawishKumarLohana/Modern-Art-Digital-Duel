package players;

import paintings.Painting;

import java.util.concurrent.ThreadLocalRandom;

public class ComputerPlayer extends Player{
    private int[][] scoreboard; // Had to Make
    public ComputerPlayer(int money,int[][] scoreboard){
        super(money,"Computer 1");
        this.scoreboard=scoreboard;
        totalPlayers++;
    }

    @Override
    public int bid(int currentBid, Painting p) {
        // Store the sum of the scores for each Artist ID
        int [] TotalSum = new int[scoreboard[0].length];

        for(int k = 0 ; k < scoreboard[0].length ;k++){
            int sumForthisRound = 0 ;
            for(int l = 0 ;l < scoreboard.length;l++){
                // add the score for each artist id
                sumForthisRound+=scoreboard[l][k];
            }
            TotalSum[k]=sumForthisRound;
        }

        // Now Total Sum has all the sums for each Artists
        // Now we have the score of that particular painting
        // Random Number generated has to be 0 - TotalSUm + 30
        int MaxRandomNum = TotalSum[p.getArtistId()];
        MaxRandomNum=MaxRandomNum+30; // 30 added to that paitning score
        // If it is the owner than max/2
        if(p.getOwner()==this){
            // it is the owner
            MaxRandomNum=MaxRandomNum/2; // Half of its Potential Value
        }

        // BUT it can't bid more than it's money. So in the the MaxRandomNum > money than we basically make the money as the max random number

        if(MaxRandomNum>getMoney()){
            MaxRandomNum=getMoney();
        }


        int randomBid = ThreadLocalRandom.current().nextInt(0,MaxRandomNum+1);
        //System.out.println("Bid: "+randomBid); // Understanding Purposes
        /*
        if(randomBid>currentBid){
            System.out.println(this.getName()+" bids "+randomBid);
        }

         */
        return randomBid;
    }

    @Override
    public Painting playPainting() {
        // Work on how the Computer Plays the paintings
        /**
         * To let the player to put up a painting for auction
         * After this method, the painting should be removed from handPaintings
         *
         * Validation of user's input should be done in this method,
         * such as checking if the index is valid. If it is invalid,
         * the player will need to enter the index again.
         */
        if (handPaintings.size() == 0) {
            return null;
        }else{
            int randPaintingIndex  = ThreadLocalRandom.current().nextInt(0,handPaintings.size());
            return removePaintingFromHand(randPaintingIndex);

        }



    }
    private Painting removePaintingFromHand(int index) {
        if (index < 0 || index >= handPaintings.size()) {
            return null;
        }
        Painting Painting = handPaintings.get(index);
        handPaintings.remove(index);
        return Painting;
    }

    @Override
    public int fixPrice() {
        // Testing, Checking why the game crashes
        //System.out.println(getMoney());
        return ThreadLocalRandom.current().nextInt(0,getMoney()+1);
        // Fixing Price Mechanism not specificed hence made it  random here

    }
    @Override
    public String toString() {
        return this.getName() + " has $??";
    }
}
