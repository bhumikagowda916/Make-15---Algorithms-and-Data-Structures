import java.util.*;
import java.io.*;

class MakeFifteen {
    public Deck deck;
    Hand playerHand;
    int score;
    public Scanner scanner;
    List<String> replayLog;
    private static final int MAX_HIGH_SCORES = 5;
    List<String> highScores;

    public MakeFifteen() {
        this.deck = new Deck();
        this.playerHand = new Hand();
        this.score = 0;
        this.scanner = new Scanner(System.in);
        this.replayLog = new ArrayList<>();
        this.highScores = loadHighScores();
    }

    public void dealInitialHand() {
        for (int i = 0; i < 5; i++) {
            playerHand.addCard(deck.dealCard());
        }
    }

    public void playGame() {
        boolean playAgain = true; // Flag to allow multiple rounds
        while (playAgain) {
            score = 0; //
            playerHand = new Hand();
            deck = new Deck();
            replayLog.clear();

            displayHighScores();
            dealInitialHand();
            System.out.println("Welcome to Make-15 Card Game!\n");

            while (!deck.isEmpty() && playerHand.size() > 0) {
                if (deck.isEmpty()) {
                    System.out.println("The deck is empty. No further cards can be dealt.");
                    break;
                }

                Card computerCard = deck.dealCard();
                System.out.println("Computer's card: " + computerCard);
                System.out.println("Your hand: \n" + playerHand);

                replayLog.add("Computer's card: " + computerCard + "\nYour hand: \n" + playerHand);

                System.out.print("Choose a card to play (1 to " + playerHand.size() + "): ");
                int choice = getValidInput(1, playerHand.size()) - 1;
                Card playerCard = playerHand.playCard(choice);

                replayLog.add("Player chose: " + playerCard);

                int total = computerCard.getValue() + playerCard.getValue();

                if (total == 15) {
                    score++;
                    System.out.println("You made 15! Your score is now: " + score);
                    playerHand.addCard(deck.dealCard());
                    replacePictureCards(); // Call the updated replacePictureCards
                } else if (computerCard.getSuit().equals(playerCard.getSuit())) {
                    System.out.println("You matched the suit! No points scored, but you can continue.");
                    playerHand.addCard(deck.dealCard());
                } else {
                    System.out.println("No valid move! Game over.");
                    break;
                }
            }

            System.out.println("Final score: " + score);
            replayLog.add("Final score: " + score);
            updateHighScores();
            saveHighScores();
            displayHighScores();
            offerReplay();

            System.out.print("Would you like to play another round? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            playAgain = response.equals("yes"); // Continue if the player chooses "yes"
        }

        System.out.println("Thank you for playing Make-15 Card Game!");
    }


    void replacePictureCards() {
        System.out.print("You scored a point! Would you like to replace your picture cards? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            for (int i = playerHand.size() - 1; i >= 0; i--) { // Iterate in reverse
                Card card = playerHand.getCard(i);
                if (card.getRank().equals("J") || card.getRank().equals("Q") || card.getRank().equals("K")) {
                    playerHand.playCard(i); // Remove the picture card
                    if (!deck.isEmpty()) {
                        playerHand.addCard(deck.dealCard()); // Replace with a new card if deck is not empty
                    } else {
                        System.out.println("Deck is empty. Unable to replace all picture cards.");
                    }
                }
            }
            System.out.println("Your picture cards have been replaced.");
        } else {
            System.out.println("You chose not to replace your picture cards.");
        }
    }



    public void updateHighScores() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        String entry = name + " - Score: " + score;
        highScores.add(entry);

        // Sort by score in descending order
        highScores.sort((a, b) -> {
            try {
                int scoreA = Integer.parseInt(a.split(": ")[1]);
                int scoreB = Integer.parseInt(b.split(": ")[1]);
                return Integer.compare(scoreB, scoreA);
            } catch (NumberFormatException e) {
                return 0;
            }
        });

        // Limit the size of the high scores list
        if (highScores.size() > MAX_HIGH_SCORES) {
            highScores.remove(highScores.size() - 1);
        }
    }

    private void displayHighScores() {
        System.out.println("High Scores:");
        for (String highScore : highScores) {
            System.out.println(highScore);
        }
    }

    void offerReplay() {
        System.out.print("Would you like to view the replay? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            for (String log : replayLog) {
                System.out.println(log);
            }
        }
    }

    private int getValidInput(int min, int max) {
        int input;
        while (true) {
            try {
                input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.print("Invalid input. Please enter a number between " + min + " and " + max + ": ");
        }
    }

    List<String> loadHighScores() {
        List<String> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("highscores.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(line);
            }
        } catch (IOException e) {
            System.out.println("No previous high scores found.");
        }
        return scores;
    }

    void saveHighScores() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("highscores.txt"))) {
            for (String score : highScores) {
                writer.write(score);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save high scores.");
        }
    }

    public static void main(String[] args) {
        MakeFifteen game = new MakeFifteen();
        game.playGame();
    }
}
