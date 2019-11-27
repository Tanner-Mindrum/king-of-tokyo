import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class PlayGame {

    public static void main(String[] args) {

        // Use this scanner for user input
        Scanner input = new Scanner(System.in);
        boolean valid = false;

        // Display welcome to game statement
        System.out.println("=========================");
        System.out.println("Welcome to King of Tokyo!");
        System.out.println("=========================\n");

        // Obtain number of players and validate input
        String numPlayers = "";
        System.out.println("How many people are playing?: ");
        while (!valid) {
            numPlayers = input.nextLine();
            while (!isNumber(numPlayers)) {  // Checks if input is a number
                System.out.println("Please enter a REAL number: ");
                numPlayers = input.nextLine();
            }
            // Now check if number of players is in the supported range of players that can play
            if (2 <= Integer.parseInt(numPlayers) && Integer.parseInt(numPlayers) <= 6) {
                valid = true;
            }
            else {
                System.out.println("You can't play with that many players! Try again: ");
            }
        }

        System.out.println("\n" + numPlayers + " players in the game!");

        // Display menu of monsters
        System.out.println("\nMonsters\n--------\n1. Alienoid\n2. Cyber Bunny\n3. Giga Zaur\n" +
                "4. Kraken\n5. Meka Dragon\n6. The King\n");

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
            System.out.println("Player " + i + " enter the name of the monster you wish to play: ");
            String monsterInput = input.nextLine();
            while (!monsterList.contains(monsterInput)) {  // Validate their monster exists
                System.out.println("Either that monster does not exist, or someone else already has it!\nTry again:");
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

        // Deal the first three power cards face up
        System.out.println("\nFirst three cards:");
        System.out.println("------------------");
        System.out.print(deck.dealPowerCard().getPowerCardName() + " | ");
        System.out.print(deck.dealPowerCard().getPowerCardName() + " | ");
        System.out.println(deck.dealPowerCard().getPowerCardName());

        // Create a pile of tokens
        int smokeToken = 3;
        int mimicToken = 1;
        int shrinkToken = 12;
        int poisonToken = 12;

        // Determine which player goes first
        System.out.println("\nThe player who rolls the most smashes goes first.\n");
        Dice diceRoll = new Dice();
        ArrayList<Integer> smashCounts = new ArrayList<Integer>();
        ArrayList<Monster> monstersDiceRoll = new ArrayList<Monster>(monstersInGame);
        int maxSmashCount = 0;

        while (monstersDiceRoll.size() > 1) {  // While there are monsters with the same number smash dice roll

            maxSmashCount = 0;

            for (int i = 0; i < monstersDiceRoll.size(); i++) {  // Each player rolls the dice

                int smashCount = 0;

                diceRoll.rollDice();  // Roll and display roll
                System.out.println(monstersDiceRoll.get(i).getName() + " rolls "
                        + diceRoll.returnDice());

                ArrayList<String> rollDice = diceRoll.returnDice();  // Count number of smashes in dice roll
                for (int j = 0; j < rollDice.size(); j++) {
                    if (rollDice.get(j).equals("Smash")) {
                        smashCount++;
                    }
                }
                smashCounts.add(smashCount);  // Add to list of smash counts

                if (smashCount > maxSmashCount) {  // Find largest smash count
                    maxSmashCount = smashCount;
                }

                diceRoll.clearDice();  // Clear dice for next roll
            }

            int removeCount = 0;  // Used to work with lists; if element is removed, entire list length decreases
            /* Remove monsters who do not have the highest dice roll. If there is one left, we've found the highest
             roller! Otherwise, loop again. */
            for (int i = 0; i < smashCounts.size(); i++) {
                if (smashCounts.get(i) != maxSmashCount) {
                    monstersDiceRoll.remove(i - removeCount);
                    removeCount++;
                }
            }

            smashCounts.clear();  // Reset smash counts
            System.out.println();  // Newline for display
        }

        System.out.println(monstersDiceRoll.get(0).getName() + " goes first!");

        /* Add monsters to a final array list of monsters in their correct orders. They are ordered by highest roller
         then by monsters on their left */
        ArrayList<Monster> finalMonstersInGame = new ArrayList<Monster>();
        for (int i = monstersInGame.indexOf(monstersDiceRoll.get(0)); i < monsterList.size(); i++) {
            finalMonstersInGame.add(monstersInGame.get(i));
        }
        for (int i = 0; i < monstersInGame.indexOf(monstersDiceRoll.get(0)); i++) {
            finalMonstersInGame.add(monstersInGame.get(i));
        }

        System.out.println("Turn order:");
        for (int i = 0; i < finalMonstersInGame.size(); i++) {
            System.out.println(i+1 + " - " + finalMonstersInGame.get(i).getName());
        }

        // TODO: BEGIN GAMEPLAY LOOP
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

//    public static String getInput(int menuRange, boolean valid) {
//        Scanner in = new Scanner(System.in);
//        String input = "";
//        while (!valid) {  // Validate their monster exists
//            input = in.nextLine();
//            while (!isNumber(input)) {  // Checks if input is a number
//                System.out.println("Please enter a REAL number: ");
//                input = in.nextLine();
//            }
//
//            if (1 <= Integer.parseInt(input) && Integer.parseInt(input) <= menuRange) {
//                valid = true;
//            }
//            else {
//                System.out.println("That option does not exist! Try again: ");
//            }
//        }
//        return input;
//    }
}