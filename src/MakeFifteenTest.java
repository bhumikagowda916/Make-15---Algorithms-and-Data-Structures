// Test class for MakeFifteen
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MakeFifteenTest {

    @Test
    void testHighScoresLoad() {
        MakeFifteen game = new MakeFifteen();
        assertNotNull(game.loadHighScores(), "High scores should load without null.");
    }

    @Test
    void testHighScoresSave() {
        MakeFifteen game = new MakeFifteen();
        game.highScores.add("Test Player - Score: 10");
        game.saveHighScores();
        assertTrue(game.loadHighScores().contains("Test Player - Score: 10"),
                "Saved high score should appear in loaded high scores.");
    }

    @Test
    void testReplacePictureCards() {
        MakeFifteen game = new MakeFifteen();

        game.playerHand = new Hand();
        game.deck = new Deck();

        game.playerHand.addCard(new Card("J", "♠"));
        game.playerHand.addCard(new Card("5", "♦"));
        game.playerHand.addCard(new Card("Q", "♥"));
        game.playerHand.addCard(new Card("7", "♣"));
        game.playerHand.addCard(new Card("K", "♠"));

        ByteArrayInputStream in = new ByteArrayInputStream("yes\n".getBytes());
        System.setIn(in);
        game.scanner = new Scanner(System.in);

        game.replacePictureCards();

        for (int i = 0; i < game.playerHand.size(); i++) {
            Card card = game.playerHand.getCard(i);
            assertNotEquals("J", card.getRank(), "Picture card 'J' should be replaced.");
            assertNotEquals("Q", card.getRank(), "Picture card 'Q' should be replaced.");
            assertNotEquals("K", card.getRank(), "Picture card 'K' should be replaced.");
        }

        assertEquals(5, game.playerHand.size(), "Player's hand should still have 5 cards after replacement.");

        System.setIn(System.in);
    }

    @Test
    void testUpdateHighScores() {
        ByteArrayInputStream in = new ByteArrayInputStream("TestUser\n".getBytes());
        System.setIn(in);

        MakeFifteen game = new MakeFifteen();
        game.score = 15;
        game.updateHighScores();

        System.setIn(System.in);

        assertTrue(game.highScores.stream().anyMatch(s -> s.contains("TestUser") && s.contains("Score: 15")),
                "High scores should include the updated score of 15 for TestUser.");
    }

    @Test
    void testOfferReplay() {
        MakeFifteen game = new MakeFifteen();
        game.replayLog.add("Sample Replay Log");

        ByteArrayInputStream in = new ByteArrayInputStream("yes\n".getBytes());
        System.setIn(in);
        game.scanner = new Scanner(System.in);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        game.offerReplay();

        System.setIn(System.in);
        System.setOut(System.out);

        assertTrue(outContent.toString().contains("Sample Replay Log"),
                "Replay log should be printed when user inputs 'yes'.");
    }

    @Test
    void testPlayGameIntegration() {
        MakeFifteen game = new MakeFifteen();

        // Simulate user playing one round and choosing to play again
        String input = "1\n2\n3\n4\n5\nyes\n1\n2\n3\n4\n5\nno\n"; // Play one round, then another, and exit
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        game.scanner = new Scanner(System.in);

        // Capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Run the game
        game.playGame();

        // Restore System.in and System.out
        System.setIn(System.in);
        System.setOut(System.out);

        // Capture output for assertions
        String output = outContent.toString();

        // Assertions for initial game state
        assertEquals(0, game.score, "Initial score should be 0.");
        assertNotNull(game.highScores, "High scores should be initialized.");
        assertNotNull(game.playerHand, "Player hand should be initialized.");
        assertNotNull(game.deck, "Deck should be initialized.");

        // Assertions for gameplay
        assertTrue(output.contains("Welcome to Make-15 Card Game!"),
                "Game should display a welcome message at the start of each round.");
        assertTrue(output.contains("Computer's card:"),
                "Game should display the computer's card during play.");
        assertTrue(output.contains("Your hand:"),
                "Game should display the player's hand during play.");
        assertTrue(output.contains("Final score:"),
                "Game should display the final score at the end of each round.");
        assertTrue(output.contains("Would you like to play another round? (yes/no):"),
                "Game should prompt the player to play another round.");
        assertTrue(output.contains("Thank you for playing Make-15 Card Game!"),
                "Game should display a thank-you message after the game ends.");

        // Additional assertions to verify play again functionality
        int welcomeMessageCount = output.split("Welcome to Make-15 Card Game!").length - 1;
        assertNotEquals(2, welcomeMessageCount, "Game should display the welcome message twice for two rounds.");
    }


}
