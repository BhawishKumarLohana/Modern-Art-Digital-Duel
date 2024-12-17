package paintings;

import players.Player;

import java.awt.color.ColorSpace;
import java.util.ArrayList;
import java.util.List;

public class FixedPriceAuctionPainting extends Painting{
    public FixedPriceAuctionPainting(int artist_id){
        super(artist_id);
    }
    @Override
    public void auction(Player[] players) {
        /*
        The auctioneer chooses a price for
        the painting and annouces it aloud.
        Each player, starting with the player
        to the left of the auctioneer and then
        continuing clockwise, can buy the painting at this
        price (once someone buys it, the auction ends). If no
        one buys the painting, the auctioneer MUST buy it for
        the fixed price.
        Notice: The auctioneer may not declare a price that is
        more than the amount of money they currently have.
         */
        // Find who is the owner of this painting
        int fixedPrice = 0 ;
        int AuctionerIndex = 0 ;
        Player CurrentAuctioner = null;
        for(int i = 0 ; i < players.length;i++){
            if(this.owner == players[i] ){
                CurrentAuctioner = players[i];
                AuctionerIndex=i;
            }
        }
       //System.out.println(CurrentAuctioner.getName()+" please fix a price for the auction:");

        // Fixing Price - Auctioner

        if(CurrentAuctioner.getName().equalsIgnoreCase("Computer 1") || CurrentAuctioner.getName().equalsIgnoreCase("Player 0") ) {
            fixedPrice = CurrentAuctioner.fixPrice();
        }else{
            fixedPrice = CurrentAuctioner.bid(currentBid,this);
        }




        System.out.println("The fixed price is "+fixedPrice);

        // Make Players Make the Bids

        // Things a bit messy here, need some adjustment
        int currentPlayer = AuctionerIndex+1;
        if(currentPlayer>=players.length){currentPlayer=0;}
        int bidAmount = 0 ;
        boolean isSold=false;

        while (currentPlayer!=AuctionerIndex) {
            Player player = players[currentPlayer];
            currentPlayer = (currentPlayer + 1) % (players.length);
            bidAmount = player.bid(currentBid, this);

            if(bidAmount<fixedPrice){
                System.out.println(player.getName()+" pass");
            }
            /*
            else if (bidAmount== 0 && player.getName().equalsIgnoreCase("Computer 1")) {
                System.out.println(player.getName()+" pass");
            }

             */
            currentBid=fixedPrice;
            if(bidAmount >= fixedPrice){
                isSold=true;
                this.setCurrentBidder(player);
                sold();
                break;
            }
        }
        if(!isSold){
            currentBid=fixedPrice;
            this.setCurrentBidder(CurrentAuctioner);
            sold();
        }
    }

    @Override
    public String getType() {
        return "Fixed Price Auction";
    }
}
