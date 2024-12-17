package paintings;
import players.*;

/**
 * #update
 * The Painting class represents a painting in the game.
 * A painting has an artist, an owner, a current bidder and a current bid.
 * 
 * Each painting has a type of auction. In this assignment, all paintings
 * have the same type of auction, which is "Open Auction".
 * 
 * You are not allowed to add any new field to this class
 * You are not allowed to add any new public method to this class
 * You can, however, add methdod with protected or private visibility
 */
public abstract class Painting {
    /**
     * The artist ID of the painting, should be between 0 and 4.
     */
    private final int artist_id;
    /**
     * #updated - change visibility
     * The owner of the painting.
     * 
     * When the painting is dealt to a player, the owner is set to that player.
     * When the painting is sold in the auction, the owner is set to the player 
     * that won the auction.
     * After the painting is sold to the bank after each round, the owner 
     * information is irrelevant and can be set as any value.
     * 
     */
    protected Player owner;
    /**
     * #updated - change visibility
     * The current bidder of the painting.
     */
    protected Player currentBidder;
    /**
     * #updated - change visibility
     * The current bid of the painting.
     */
    protected int currentBid;
    /**
     * The names of the artists
     */
    public static final String[] ARTIST_NAMES = {"0. Manuel Carvalho", "1. Sigrid Thaler", "2. Daniel Melim", "3. Ramon Martins", "4. Rafael Silveira"};        
    
    /**
     * Constructor of the Painting class
     */
    public Painting(int artist_id) {
        currentBidder = null;
        currentBid = 0;
        this.artist_id = artist_id;
    }
    /**
     * Get the artist ID of the painting
     */
    public int getArtistId() {
        return artist_id;
    }
    /**
     * Setter of owner
     */
    public void setOwner(Player p) {
        owner = p;
    }
    /**
     * #updated
     * The type of auction of the painting
     */
    // IMPLEMENTATION REQUIRED
    public abstract String getType();
    /**
     * Getter of owner
     */
    public Player getOwner() {
        return owner;
    }
    /**
     * Get the name of the artist
     */
    public String getArtistName() {
        return ARTIST_NAMES[artist_id];
    }
    /**
     * #updated, given
     * 
     * Sold the painting to the current bidder
     * This method has been completed for you.
     * You should not modify this method.
     */
    protected void sold() {
        System.out.print("Sold! - ");
        if (currentBidder == null || owner == currentBidder) {
            System.out.println(this.toString() + " is sold to " + owner.getName() + " for " + currentBid);
            //owner get the painting automatically
            owner.buyPainting(this);
            owner.pay(currentBid); //owner pay to the bank
        } else {
            System.out.println(this.toString() + " is sold to " + currentBidder.getName() + " for " + currentBid);
            //currentBidder get the painting
            currentBidder.buyPainting(this);
            currentBidder.pay(currentBid);
            //owner get the money
            owner.earn(currentBid);
            owner = currentBidder;
        }
        
    }
    /**
     * #updated - final
     * 
     * toString method to be modified
     */
    @Override
    public final String toString() {
        return getArtistName() + " [" + getType() + "] owner: " + (owner == null ? "null" : owner.getName());
    }


    /**
     * #updated - abstract method
     * The auction method - various according to the type of auction
     */
    public abstract void auction(Player[] players);


    public void setCurrentBidder(Player currentBidder) {
        this.currentBidder = currentBidder;
    }
}
// There is no bid method here
