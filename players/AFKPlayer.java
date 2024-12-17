package players;

import paintings.Painting;

import java.util.concurrent.ThreadLocalRandom;

public class AFKPlayer extends  Player{
    public AFKPlayer(int money){
        super(money,"AFK " + totalPlayers++);
    }

    @Override
    public int bid(int currentBid, Painting p) {
        return 0;
    }

    @Override
    public Painting playPainting() {
        if (handPaintings.size() == 0) {
            return null;
        }else{
            int PaintingIndex  = 0;
            return removePaintingFromHand(PaintingIndex);

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
}
