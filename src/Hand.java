import java.util.*;

class Hand {
    private List<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        if (card != null) {
            cards.add(card);
        }
    }

    public Card playCard(int index) {
        return cards.remove(index);
    }

    public Card getCard(int index) {
        return cards.get(index);
    }

    public int size() {
        return cards.size();
    }

    public Iterator<Card> iterator() {
        return cards.iterator();
    }

    @Override
    public String toString() {
        StringBuilder handDisplay = new StringBuilder();
        for (int i = 0; i < cards.size(); i++) {
            handDisplay.append("Card(").append(i + 1).append(") ").append(cards.get(i).toString()).append("\n");
        }
        return handDisplay.toString();
    }
}
