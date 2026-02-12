public class Card {
    private String rank;
    private String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        switch (rank) {
            case "J": return 11;
            case "Q": return 11;
            case "K": return 11;
            case "A": return 12;
            default: return Integer.parseInt(rank);
        }
    }

    @Override
    public String toString() {
        return rank + suit;
    }
}
