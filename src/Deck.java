
import java.util.*;

class Deck {
    private List<Card> cards;

    public Deck() {
        String[] suits = {"♠", "♦", "♣", "♥"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        cards = new ArrayList<>();
        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(cards);
    }

    public Card dealCard() {
        return cards.isEmpty() ? null : cards.remove(cards.size() - 1);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
