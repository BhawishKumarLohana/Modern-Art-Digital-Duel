package paintings;

import players.Player;

import java.util.ArrayList;
import java.util.List;

public class OneOfferAuctionPainting extends Painting{
    public OneOfferAuctionPainting(int artist_id){
        super(artist_id);
    }

    /*
    Starting with the player to the left of
    the auctioneer, and moving clockwise
    around the table, each player may
    choose not to bid and pass or they can bid by
    stating an amount higher than the previous bid. The
    auctioneer has the last chance to bid.
    After the auctioneer bids or passes, the auction ends.
    If no players make any bids, then the auctioneer gets
    the painting for free.
     */

    @Override
    public void auction(Player[] players) {
        // Find who is the one bidding
        int AuctionerIndex = 0 ;
        Player CurrentAuctioner = null;
        for(int i = 0 ; i < players.length;i++){
            if(this.owner == players[i] ){
                CurrentAuctioner = players[i];
                AuctionerIndex=i;
            }
        }
        int currentPlayer = AuctionerIndex+1;
        if(currentPlayer>=players.length){currentPlayer=0;}
        //int PlayerCounter = currentPlayer;
        List<Integer> bids = new ArrayList<>();
        List<Integer> bidIndexes =  new ArrayList<>();

        while (currentPlayer!=AuctionerIndex) {
            Player player = players[currentPlayer];
            int bid = player.bid(currentBid,this);
            System.out.println(player.getName()+" bids $"+bid);
            bids.add(bid);
            bidIndexes.add(currentPlayer);
            currentPlayer = (currentPlayer + 1) % players.length;
            if(currentPlayer>=players.length){currentPlayer=0;} // add this everywhere
        }
        // Check if nobody bid
        int sum = 0 ;
        for(int i = 0 ; i < bids.size();i++){if(bids.get(i)!=null){sum+=bids.get(i);}}
        if(sum==0){
            // No one bid
            // Give the paining to player for free
            currentBid=0;
            this.setCurrentBidder(CurrentAuctioner);
            sold();
        }else{
            // someone bid so last chance to bid given to player

            int bid = players[AuctionerIndex].bid(currentBid,this);
            bids.add(bid);
            bidIndexes.add(AuctionerIndex);
            System.out.println(players[AuctionerIndex].getName()+" bids $"+bid);
            int maxBid = maxBid(bids);
            int maxBidIndex = bids.indexOf(maxBid); // Index of the maxBid

            //System.out.println("Max Bid:" + maxBid);
            //System.out.println("Max Bid Index:" + maxBidIndex);
            currentBid=maxBid;
            // This needs to be updated to give the painting to the auctioner in case others bid 0
            this.setCurrentBidder(players[bidIndexes.get(maxBidIndex)]);
            sold();
        }
    }

    @Override
    public String getType() {
        return "One Offer Auction";
    }

    public int maxBid(List<Integer> bids){
        int maxBid = Integer.MIN_VALUE;
        for(int i = 0 ;  i < bids.size();i++){
            if(bids.get(i)>maxBid){
                maxBid=bids.get(i);
            }
        }
        return maxBid;
    }

    public int maxBidIndex(List<Integer> bids){
        int maxBidIndex = -1;
        int maxBid =  Integer.MIN_VALUE;
        for(int i = 0 ;  i < bids.size();i++){
            if(bids.get(i)>maxBid){
                maxBid=bids.get(i);
                maxBidIndex=i;
            }
        }
        return maxBidIndex;
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
        return maxIndexes.get(0);

    }
}
