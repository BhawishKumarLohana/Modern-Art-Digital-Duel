package players;
import paintings.Painting;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


/**
 * #updated
 * This class represents a player in the ModernArt game
 * 
 * You are not allowed to add any new field to this class
 * You are not allowed to add any new public method to this class
 */
public class Player {
   /**
     * The name of the player
     * 
     * The first player should have the name "[PlayerType] 0"
     * The second player should have the name "[PlayerType] 1"
     * The third player should have the name "[PlayerType] 2"
     * ...
     * PlayerType is set to Player for human player, 
     * and Computer for Computer Player, AFK for AFK player...
     */
    private final String name;
    /**
     * The money the player has
     */
    private int money;
    /**
     * #updated - change visibility
     * The total number of players in the game
     */
    protected static int totalPlayers = 0;
    /**
     * #updated - change visibility
     * The paintings the player has in hand
     */
    protected List<Painting> handPaintings = new ArrayList<>();
    /**
     * #updated - change visibility
     * The paintings the player has bought
     */
    protected List<Painting> boughtPaintings = new ArrayList<>();
    /**
     * #added
     * Constructor of the Player class
     */
    public Player(int money, String name) {
        this.money = money;
        this.name = name;
    }
    /**
     * Constructor of the Player class
     */
    public Player(int money)
    {
        this(money, "Player " + totalPlayers++);
    }
    /**
     * To deal a painting to the player
     */
    public void dealPaintings(Painting painting) {
        handPaintings.add(painting);
        painting.setOwner(this);
    }
    /**
     * #update final
     * Get the name of the player
     */
    public final String getName() {
        return name;
    }

    private Painting removePaintingFromHand(int index) {
        if (index < 0 || index >= handPaintings.size()) {
            return null;
        }
        Painting Painting = handPaintings.get(index);
        handPaintings.remove(index);
        return Painting;
    }
    /**
     * To let the player to put up a painting for auction
     * After this method, the painting should be removed from handPaintings
     * 
     * Validation of user's input should be done in this method,
     * such as checking if the index is valid. If it is invalid,
     * the player will need to enter the index again.
     */
    public Painting playPainting() {
        int index = -1;
        if (handPaintings.size() == 0) {
            return null;
        }
        Scanner scanner = new Scanner(System.in);
        do {
            try {
                System.out.println(this);
                int i = 0;
                for (Painting p : handPaintings) {
                    System.out.println(i++ + ": " + p);
                }
                System.out.print("Please enter the index of the Painting you want to play: ");
                index = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
            }
        } while (index < 0 || index >= handPaintings.size());
        return removePaintingFromHand(index);
    }
    /**
     * #update final, given
     * Get the money the player has
     */
    public final int getMoney() {
        return money;
    }
    /**
     * #update - another parameter is added; given
     * 
     * 
     * To let the player to bid. 
     * 
     * In some auctions, e.g. open auction, the player knows the current bid.
     * In this case the currentBid will be passed to the method.
     * 
     * In some auctions, e.g. blind auction, the player does not know the current bid.
     * In this case, the currentBid passed to the method will be 0.
     * 
     * A human player should be asked to input the bid amount.
     * The bid amount should be less than or equal to the money the player has.
     * If the bid amount is too high, the player should be asked to input again.
     * 
     * If the bid amount is too small (less than the current bid or less than 1),
     * the bid amount will also be returned, which may means to pass the bid.
     * 
     * You should not assume there is only open auction when writing this method
     */
    public int bid(int currentBid, Painting p) {
        Scanner scanner = new Scanner(System.in);
        do {
            try {
                System.out.println(this);
                System.out.print("Enter your bid (enter 0 = forfeit): ");
                int bid = scanner.nextInt();
                if (bid > money)
                    continue;
                return bid;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
            }
        } while (true);


    }


    /**
     * #update - final, given
     * 
     * This old version is deprecated and shall not be used
     * outside this package.
     */
    /*
    @Deprecated
    protected int bid(int currentBid) {
        Scanner scanner = new Scanner(System.in);
        do {
            try {
                System.out.println(this);
                System.out.print("Enter your bid (enter 0 = forfeit): ");
                int bid = scanner.nextInt();
                if (bid > money)
                    continue;
                return bid;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
            }
        } while (true);
    }

     */
    /**
     * To let the player to pay
     */
    public void pay(int amount) {
        money -= amount;
    }
    /**
     * To let the player to earn
     */
    public void earn(int amount) {
        money += amount;
    }
    /**
     * toString method that you need to override
     */
    public String toString() {
        return name + " has $" + money;
    }
    /**
     * To finalize a bid and purchase a painting
     * 
     * This method has been finished for you
     */
    public void buyPainting(Painting Painting) {
        boughtPaintings.add(Painting);
    }
    /**
     * To sell all the paintings the player has bought to the bank 
     * after each round
     */    
    public void sellPainting(int[] scores) {
        for (Painting Painting : boughtPaintings) {
            money += scores[Painting.getArtistId()];
        }
        boughtPaintings.clear();
    }
    public  int fixPrice(){
        // Human Player can use bid method so return dummy value here
        Scanner scanner = new Scanner(System.in);

        do {
            try {
                System.out.println(this.getName() +" please fix a price for the auction:");
                System.out.println(this);
                System.out.print("Enter your bid (enter 0 = forfeit): ");
                int bid = scanner.nextInt();
                if (bid > money|| bid < 0 ) {

                    continue;
                }
                return bid;
            } catch (Exception e) {
                System.out.print("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
            }
        } while (true);

    };


}
