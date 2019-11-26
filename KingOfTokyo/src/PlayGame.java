import java.util.ArrayList;
import java.util.Scanner;

public class PlayGame {

    public static void main(String[] args) {

        // Use this scanner for user input
        Scanner input = new Scanner(System.in);

        // Display welcome to game statement
        System.out.println("=========================");
        System.out.println("Welcome to King of Tokyo!");
        System.out.println("=========================\n");

        // Obtain number of players and validate input
        System.out.println("How many people are playing?: ");
        String numPlayers = input.nextLine();
        while (!isNumber(numPlayers)) {
            System.out.println("Please enter a REAL number!");
            numPlayers = input.nextLine();
        }

        // Display menu of monsters
        System.out.println("1. Alienoid\n2. Cyber Bunny\n3. Giga Zaur\n4. Kraken\n5. Meka Dragon\n6. The King\n");

        // Generate Arraylist of valid monsters for the user to choose from
        ArrayList<String> monsterList = new ArrayList<String>();
        monsterList.add("Alienoid");
        monsterList.add("Cyber Bunny");
        monsterList.add("Giga Zaur");
        monsterList.add("Kraken");
        monsterList.add("Meka Dragon");
        monsterList.add("The King");

        // This is a list of each player's monster. A player is referred to by their monster throughout the game.
        // To choose a specific player use monstersInGame.get(PLAYER'S NUMBER);
        ArrayList<Monster> monstersInGame = new ArrayList<Monster>();

        // Player selects their monster and we validate their monster selection and remove that monster from list
        for (int i = 1; i <= Integer.parseInt(numPlayers); i++) {

            System.out.println("Player " + i + " choose your monster: ");
            String monsterInput = input.nextLine();
            while (!monsterList.contains(monsterInput)) {  // Validate their monster exists
                System.out.println("That monster does not exist!\nTry again:");
                monsterInput = input.nextLine();
            }
            monsterList.remove(monsterInput);  // Remove their monster from the list so no other player can play as it

            // Create a new monster object and add it to the list of monsters in this current game
            Monster monster = new Monster();
            monster.setName(monsterInput);
            monster.setHealth(10);
            monster.setVictoryPoints(0);
            monstersInGame.add(monster);
        }

        // Create deck of PowerCards used in the current game
        Deck deck = new Deck();
        deck.makeDeck();
        deck.shuffle();
        deck.displayDeck();

        System.out.println(deck.dealPowerCard().getPowerCardName());
        System.out.println(deck.dealPowerCard().getPowerCardName());
        System.out.println(deck.dealPowerCard().getPowerCardName());

        System.out.println("The player who rolls the most smashes starts first");
        Dice diceRoll = new Dice();
        int maxSmashCount = 0;
        String maxMonster = "";
        for (int i = 0; i < monstersInGame.size(); i++) {
            int smashCount = 0;
            diceRoll.rollDice();
            System.out.println("Player " + (i + 1) + "'s monster " + monstersInGame.get(i).getName() +  " rolls " + diceRoll.returnDice());

            ArrayList<String> rollDice = diceRoll.returnDice();

            for (int j = 0; j < rollDice.size(); j++ ) {

               if (rollDice.get(j).equals("Smash")) {
                   smashCount++;
               }
               if (smashCount > maxSmashCount) {
                   maxSmashCount = smashCount;
                   maxMonster = monstersInGame.get(i).getName();
               }

               else if (smashCount == maxSmashCount) {

               }
            }

            diceRoll.clearDice();
        }

        System.out.println("This monster goes first! " + maxMonster);

        //TODO: Add tokens






    }

    public static boolean isNumber(String userInput) {
        try {
            Integer.parseInt(userInput);
        }
        catch (NumberFormatException invalid) {
            return false;
        }
        return true;
    }
}