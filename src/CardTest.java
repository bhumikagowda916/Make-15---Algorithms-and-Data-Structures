import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void testCardCreation() {
        Card card = new Card("A", "♠");
        assertEquals("A", card.getRank());
        assertEquals("♠", card.getSuit());
    }

    @Test
    void testCardValue() {
        assertEquals(12, new Card("A", "♠").getValue());
        assertEquals(11, new Card("J", "♦").getValue());
        assertEquals(5, new Card("5", "♥").getValue());
    }

    @Test
    void testCardToString() {
        Card card = new Card("10", "♣");
        assertEquals("10♣", card.toString());
    }
}
