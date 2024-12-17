package paintings;

import players.Player;

import java.util.ArrayList;
import java.util.List;

public class HiddenAuctionPainting extends Painting{
    public HiddenAuctionPainting(int artist_id){
        super(artist_id);
    }
    @Override
    public void auction(Player[] players) {
        /*
        Once all players have held out a closed fist (containing
        either a bid or nothing), everyone opens their fists
        at the same time and the highest bidder buys the
        painting. If two or more players tie for the highest
        bid, then the player sitting closest to the auctioneer in
        clockwise order wins the auction.
        If the auctioneer is one of the players who tied for the
        highest bid, then they buy the painting.
        If no players make any bids, then the auctioneer gets
        the painting for free
         */
        List<Integer> Bids = new ArrayList<>();
        for (Player p : players) {
            int x = p.bid(currentBid,this);
            if(p.getName().equalsIgnoreCase("Player 0") && x < 0){
                x = 0 ;
            }
            System.out.println(p.getName()+ " bids "+x);
            Bids.add(x);
        }
        int AuctionerIndex = 0 ;
        Player CurrentAuctioner = null;
        for(int i = 0 ; i < players.length;i++){
            if(this.owner == players[i] ){
                CurrentAuctioner = players[i];
                AuctionerIndex=i;
            }
        }
        int maxBidIndex = findMaxBidIndex(Bids,AuctionerIndex);
        this.setCurrentBidder(players[maxBidIndex]);
        this.currentBid=Bids.get(maxBidIndex);
        this.sold();


    }
    public int findMaxBidIndex(List<Integer> bids,int AcIndex){
        int maxBid = Integer.MIN_VALUE;
        List<Integer> maxIndexes =  new ArrayList<>();
        for(int thisIndex = 0 ; thisIndex < bids.size();thisIndex++){
            if(bids.get(thisIndex)>maxBid){
                maxBid=bids.get(thisIndex);
                maxIndexes.clear();
                maxIndexes.add(thisIndex);
            } else if (bids.get(thisIndex)==maxBid) {
                maxIndexes.add(thisIndex);
            }
        }
        // Now if there is a collision than maxIndexes>1
        if(maxIndexes.size()==1){
            return maxIndexes.get(0);
        }
        // Now we try to find the closest possible player to the AcIndex
        int closestIndex = -1;
        int closestDiff = 100; // VERY LARGE VALUE
        for(int eachIndex  = 0 ; eachIndex < maxIndexes.size();eachIndex++){
                // should not be itself
            int thisIndex =  maxIndexes.get(eachIndex);
            int Diff = DiffCalc(thisIndex,AcIndex);
            if(Diff < closestDiff){
                closestDiff=Diff;
                closestIndex=thisIndex;
            }

        }

        return closestIndex;

    }

    public int DiffCalc(int x , int y){
        if(x>y){
            return x - y ;
        }else{
            return y-x;
        }
    }

    @Override
    public String getType() {
        return "Hidden Auction";
    }
}
