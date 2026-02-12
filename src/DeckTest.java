import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void testDeckCreation() {
        Deck deck = new Deck();
        assertFalse(deck.isEmpty());
    }

    @Test
    void testDeckShuffle() {
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        assertNotEquals(deck1.dealCard().toString(), deck2.dealCard().toString());
    }

    @Test
    void testDealCard() {
        Deck deck = new Deck();
        assertNotNull(deck.dealCard());
        while (!deck.isEmpty()) {
            deck.dealCard();
        }
        assertNull(deck.dealCard());
    }

    @Test
    void testDeckEmpty() {
        Deck deck = new Deck();
        while (!deck.isEmpty()) {
            deck.dealCard();
        }
        assertTrue(deck.isEmpty());
    }
}