import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    @Test
    void testAddCard() {
        Hand hand = new Hand();
        Card card = new Card("7", "♠");
        hand.addCard(card);
        assertEquals(1, hand.size());
    }

    @Test
    void testPlayCard() {
        Hand hand = new Hand();
        Card card = new Card("7", "♠");
        hand.addCard(card);
        Card playedCard = hand.playCard(0);
        assertEquals("7", playedCard.getRank());
        assertTrue(hand.size() == 0);
    }

    @Test
    void testGetCard() {
        Hand hand = new Hand();
        Card card = new Card("8", "♣");
        hand.addCard(card);
        assertEquals("8", hand.getCard(0).getRank());
    }

    @Test
    void testHandSize() {
        Hand hand = new Hand();
        assertEquals(0, hand.size());
        hand.addCard(new Card("9", "♦"));
        assertEquals(1, hand.size());
    }

    @Test
    void testToString() {
        Hand hand = new Hand();
        hand.addCard(new Card("10", "♣"));
        assertTrue(hand.toString().contains("10♣"));
    }
}
