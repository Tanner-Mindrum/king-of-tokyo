import java.io.*;
import java.util.*;

public class Deck {

    private String csvPath;
    private String line;
    private String csvSplitter;
    private ArrayList<PowerCard> deck;  // Deck of Power Cards

    // Default constructor initializes instance variables
    public Deck() {
        // Tanner's path: "C:/CECS 343/343-KingOfTokyo-HTT/KingOfTokyo/src/cardList.csv"
        // Tymee's path: "C:/Users/tymee/IdeaProjects/king-of-tokyo/KingOfTokyo/src/cardList.csv"
        csvPath = "C:/CECS 343/343-KingOfTokyo-HTT/KingOfTokyo/src/cardList.csv";
        line = "";
        csvSplitter = ",";
        deck = new ArrayList<>();
    }

    // Creates a deck of Power Cards by iterating through a .csv file, parsing the file's column values,
    // initializing a Power Card, and adding that Power Card to the deck
    public void makeDeck() {
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {  // Read in csv content
            while ((line = br.readLine()) != null) {  // While a line of content exists in csv

                String[] card = line.split(csvSplitter);  // Split values by comma into [name, cost, type, ability]

                //Freeze Time, It has, Regeneration, Monster Sidekick, Thunderstomp
                if (card[0].equals("It Has a Child")) {
                    card[3] = "If you are eliminated discard all your cards and lose all your [Star], Heal to 10[Heart] and start again.";
                }
                else if (card[0].equals("Freeze Time")) {
                    card[3] = "On a turn where you score [1][1][1], you can take another turn with one less die.";
                }
                else if (card[0].equals("Regeneration")) {
                    card[3] = "When you heal, heal 1 extra damage.";
                }
                else if (card[0].equals("Monster Sidekick")) {
                    card[3] = "If someone kills you, Go back to 10[Heart] and lose all your [Star]. If either of you or your killer win, or all other players are eliminated then you both win. If your killer is eliminated then you are also. If you are eliminated a second time this card has no effect.";
                }
                else if (card[0].equals("Thunder Stomp")) {
                    card[3] = "\tIf you score 4[Star] in a turn, all players roll one less die until your next turn.";
                }

                deck.add(new PowerCard(card[0], Integer.parseInt(card[1]), card[2], card[3]));  // Add card to deck
            }
        }
        catch (IOException e) {  // For any strange csv errors
            e.printStackTrace();
        }
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    // Returns a PowerCard by popping the top one from the deck
    public PowerCard dealPowerCard() {
        return deck.remove(0);
    }

    public void addPowerCard(PowerCard card) {
        deck.add(card);
    }

    public void displayDeck() {
        int displayCount = 1;
        for (int i = 0; i < deck.size(); i++) {
            System.out.println("Power Card " + displayCount + ": " + deck.get(i).getPowerCardName());
            displayCount++;
        }
    }
}
