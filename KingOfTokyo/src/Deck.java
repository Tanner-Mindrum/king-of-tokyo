import java.io.*;
import java.util.*;

public class Deck {

    private String csvPath;
    private String line;
    private String csvSplitter;
    private ArrayList<PowerCard> deck;  // Deck of Power Cards

    // Default constructor initializes instance variables
    public Deck() {
        csvPath = "C:/Users/tymee/IdeaProjects/king-of-tokyo/KingOfTokyo/src/cardList.csv";
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

    public void displayDeck() {
        int displayCount = 1;
        for (int i = 0; i < deck.size(); i++) {
            System.out.println("Power Card " + displayCount + ": " + deck.get(i).getPowerCardName());
            displayCount++;
        }
    }
}
