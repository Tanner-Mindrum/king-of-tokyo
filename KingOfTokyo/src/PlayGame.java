import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.math.*;

// For Deck class csv path:
// Hunter's path: "C:\Users\Hunter\Desktop\King_of_Toyko_Copy\king-of-tokyo\KingOfTokyo\src\cardList.csv"

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
            System.out.println("Player " + i + ", enter the name of the monster you wish to play: ");
            String monsterInput = input.nextLine();
            while (!monsterList.contains(monsterInput)) {  // Validate their monster exists
                System.out.println("Either that monster does not exist, or someone else already has it!\nTry again:");
                monsterInput = input.nextLine();
            }
            monsterList.remove(monsterInput);  // Remove their monster from the list so no other player can play as it

            // Create a new monster object and add it to the list of monsters in this current game
            Monster monster = new Monster();
            monster.setName(monsterInput);
            monstersInGame.add(monster);
        }

        // Create deck of PowerCards used in the current game


        Deck deck = new Deck();
        deck.makeDeck();
        deck.shuffle();

        // Initializing and filling faceUpCards (to 3)
        ArrayList<PowerCard> faceUpCards = new ArrayList<>();
        faceUpCards.add(deck.dealPowerCard());
        faceUpCards.add(deck.dealPowerCard());
        faceUpCards.add(deck.dealPowerCard());

        // Create a pile of tokens
        int smokeToken = 3;
        int mimicToken = 1;
        int shrinkToken = 12;
        int poisonToken = 12;

        int energyPool = 50;

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

            int removeCount = 0;  // If element is removed, entire list length decreases
            /* Remove monsters who do not have the highest dice roll. If there is one left, we've found the highest
             roller! Otherwise, while loop again. */
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
        for (int i = monstersInGame.indexOf(monstersDiceRoll.get(0)); i < monstersInGame.size(); i++) {
            finalMonstersInGame.add(monstersInGame.get(i));
        }
        for (int i = 0; i < monstersInGame.indexOf(monstersDiceRoll.get(0)); i++) {
            finalMonstersInGame.add(monstersInGame.get(i));
        }

        System.out.println("\nTurn order:");
        for (int i = 0; i < finalMonstersInGame.size(); i++) {
            System.out.println(i+1 + " - " + finalMonstersInGame.get(i).getName());
        }

        int turn = 0;
        ArrayList<Monster> tokyo = new ArrayList<Monster>();

        System.out.println("\nThe game begins!");  // Play gong sound

        // Set variable for player = finalMonstersInGame.get(turn) for easy access

        while (true) {

            // TURN OVERVIEW:
            // 1. Roll Dice - a player can roll the dice 3 times [DONE]
            // 2. Resolve Dice
            // 3. Enter Tokyo
            // 4. Buy Power Cards
            // 5. End turn
            System.out.println("\n" + finalMonstersInGame.get(turn).getName() + ", it's your turn!");

            /* ~ 2. Enter Tokyo ~ */
            boolean inTokyo = false;
            for (Monster m : finalMonstersInGame) {
                if (m.getMonsterLocation()) {
                    inTokyo = true;
                }
            }

            if (!inTokyo) {
                finalMonstersInGame.get(turn).setMonsterLocation(true);
                System.out.println(finalMonstersInGame.get(turn).getName() + " has entered Tokyo!");
            }
            /* ~ 2. END Enter Tokyo ~ */

            System.out.println(finalMonstersInGame.get(turn).getName() + "'s current stats: " + "\nHealth: " + finalMonstersInGame.get(turn).getHealth() +
                    "\nVictory Points: " + finalMonstersInGame.get(turn).getVictoryPoints() +
                    "\nEnergy Cubes: " + finalMonstersInGame.get(turn).getEnergyCubes() +
                    "\nIn Tokyo: " + finalMonstersInGame.get(turn).getMonsterLocation());


            // Displaying the player's current PowerCards
            System.out.println("Displaying " + finalMonstersInGame.get(turn).getName() + "\'s kept Power Cards.");
            for (int i = 0; i < finalMonstersInGame.get(turn).getPlayerDeck().size(); i++) {
                System.out.println(finalMonstersInGame.get(turn).getPlayerDeck().get(i).getPowerCardName() + ": " +
                        finalMonstersInGame.get(turn).getPlayerDeck().get(i).getPowerCardAbility());
            }

            /* ~ 1. Roll dice ~ */
            int rollCount = 1;
            boolean stopFlag = false;
            ArrayList<String> finalDice = new ArrayList<String>();  // Hold the contents of their desired dice
            ArrayList<String> currentRoll = new ArrayList<String>();

            while (rollCount <= 3) {

                System.out.println("final dice: ");
                System.out.println(finalDice);

                if (finalDice.size() == 0) {
                    diceRoll.rollDice();
                }
                else {
                    diceRoll.rollModifiedDice(6 - finalDice.size());
                }

                System.out.println("\nRoll " + rollCount + "!");
                System.out.println(finalMonstersInGame.get(turn).getName() +
                        " rolls " + diceRoll.returnDice());

                currentRoll.addAll(diceRoll.returnDice());
                currentRoll.addAll(finalDice);

                if (rollCount != 3) {
                    finalDice.clear();
                }

                System.out.println("CURR ROLL");
                System.out.println(currentRoll);

                valid = false;

                if (rollCount != 3) {

                    System.out.println("\nSelect which dice you'd like to keep (enter numbers): ");
                    for (int i = 0; i < currentRoll.size() + 1; i++) {
                        if (i != currentRoll.size()) {
                            System.out.println(i + 1 + ". " + currentRoll.get(i));
                        } else {
                            System.out.println(i + 1 + ". Keep all (stop rolling)");
                            System.out.println(i + 2 + ". Re-roll all");
                        }
                    }
                    System.out.println("Enter selection: ");

                    while (!valid) {
                        boolean numInRange = false;
                        boolean numIsQuit = false;
                        boolean duplicate = false;

                        String diceList = input.nextLine();

                        // Check they don't enter an option and keep all
                        for (int i = 0; i < diceList.length(); i++) {
                            if (Character.isDigit(diceList.charAt(i))) {
                                if (1 <= Character.getNumericValue(diceList.charAt(i)) &&
                                        Character.getNumericValue(diceList.charAt(i)) <= currentRoll.size()
                                                + 2) {
                                    if (Character.getNumericValue(diceList.charAt(i)) == currentRoll.size()
                                            + 1) {
                                        numIsQuit = true;
                                    } else {
                                        numInRange = true;
                                    }
                                }
                            }
                        }

                        // Check duplicate
                        for (int i = 0; i < diceList.length(); i++) {
                            for (int j = i+1; j < diceList.length(); j++) {
                                if (diceList.charAt(j) == diceList.charAt(i)) {
                                    duplicate = true;
                                    break;
                                }
                            }
                        }

                        // Check if they manually enter keep all
                        int fullCount = 0;
                        for (int i = 0; i < diceList.length(); i++) {
                            if (Character.isDigit(diceList.charAt(i))) {
                                fullCount += Character.getNumericValue(diceList.charAt(i));
                            }
                        }

                        if (!(numInRange && numIsQuit || duplicate)) {
                            for (int i = 0; i < diceList.length(); i++) {
                                if (Character.isDigit(diceList.charAt(i))) {
                                    if (1 <= Character.getNumericValue(diceList.charAt(i)) &&
                                            Character.getNumericValue(diceList.charAt(i)) <=
                                                    currentRoll.size() + 2) {
                                        // Keep all
                                        if (Character.getNumericValue(diceList.charAt(i)) ==
                                                currentRoll.size() + 1 || fullCount == 21) {
                                            finalDice.clear();
                                            finalDice.addAll(currentRoll);
                                            valid = true;
                                            stopFlag = true;
                                            break;
                                        }
                                        // Re-roll all
                                        else if (Character.getNumericValue(diceList.charAt(i)) ==
                                                currentRoll.size() + 2) {
                                            valid = true;
                                        }
                                        // Keep individual
                                        else {
                                            finalDice.add(currentRoll.get(Character.getNumericValue(
                                                    diceList.charAt(i)) - 1));
                                            valid = true;
                                        }
                                    }
                                }
                            }
                        }

                        if (!valid) {
                            System.out.println("\nThat option doesn't exist!\nTry again:");
                        }
                    }
                }
                else {
                    System.out.println(diceRoll.returnDice());
                    finalDice.addAll(diceRoll.returnDice());
                }

                if (stopFlag) {
                    diceRoll.clearDice();
                    break;
                }

                diceRoll.clearDice();
                currentRoll.clear();
                rollCount++;
            }
            System.out.println("-----------------------");
            System.out.println(finalMonstersInGame.get(turn).getName() + "'s final dice: ");
            System.out.println(finalDice);
            System.out.println("-----------------------");
            /* ~ 1. END roll dice ~ */

            /* ~ 3. Resolve dice ~ */

            // finalDice.add("Smash");
            int ones = 0;
            int twos = 0;
            int threes = 0;
            int victoryPoints = 0;
            int energyCount = 0;
            int smashCount = 0;
            int healthCount = 0;

            for (int i = 0; i < finalDice.size(); i++) {
                String temp = finalDice.get(i);
                if (isNumber(temp)) {
                    if (temp.equals("1")) {
                        ones++;
                    } else if (temp.equals("2")) {
                        twos++;
                    } else if (temp.equals("3")) {
                        threes++;
                    }
                }
                else if (temp.equals("Energy") && energyPool > 0) {
                    energyCount++;
                    energyPool--;
                }
                else if (temp.equals("Smash")) {
                    smashCount++;
                }
                else if (temp.equals("Heal")) {
                    healthCount++;
                }
            }
            if (energyPool <= 0) {
                System.out.println("There are no more energy cubes in the pool");
            }
            if (ones >= 3) {
                victoryPoints += 1;
                ones -= 3;
                if (ones > 0) {
                    victoryPoints += ones;
                }
            }
            if (twos >= 3 ) {
                victoryPoints += 2;
                twos -= 3;
                if (twos > 0) {
                    victoryPoints += twos;
                }
            }
            if (threes >= 3) {
                victoryPoints += 3;
                threes -= 3;
                if (threes > 0) {
                    victoryPoints += threes;
                }
            }

            if (finalMonstersInGame.get(turn).getMonsterLocation()) {
                for (Monster m : finalMonstersInGame) {
                    if (!m.getMonsterLocation()) {
                        m.setHealth(m.getHealth() - smashCount);
                    }
                }
            }
            else {
                for (Monster m: finalMonstersInGame) {
                    if (m.getMonsterLocation()) {
                        m.setHealth(m.getHealth() - smashCount);
                        if (smashCount >= 1 && healthCount > 0) {
                            System.out.println(m.getName() + ", do you want to stay or leave Tokyo?");
                            System.out.println("1. Leave");
                            System.out.println("2. Stay");
                            System.out.println(m.getName() + "'s current stats: " + "\nHealth: " + m.getHealth() +
                                    "\nVictory Points: " + m.getVictoryPoints() +
                                    "\nEnergy Cubes: " + m.getEnergyCubes() +
                                    "\nIn Tokyo: " + m.getMonsterLocation());
                            if (Integer.parseInt(getInput(2)) == 1) {
                                m.setMonsterLocation(false);
                                System.out.println(m.getName() + " has left Tokyo!");
                            }
                        }
                    }
                }
            }

            // Heal
            if (!finalMonstersInGame.get(turn).getMonsterLocation()) {
                if (finalMonstersInGame.get(turn).getHealth() < 10) {
                    finalMonstersInGame.get(turn).setHealth(healthCount + finalMonstersInGame.get(turn).getHealth());
                }
            }
            if (finalMonstersInGame.get(turn).getHealth() > 10) {
                finalMonstersInGame.get(turn).setHealth(10);
            }

            finalMonstersInGame.get(turn).setVictoryPoints(victoryPoints + finalMonstersInGame.get(turn).getVictoryPoints());
            finalMonstersInGame.get(turn).setEnergyCubes(energyCount + finalMonstersInGame.get(turn).getEnergyCubes());

            System.out.println(finalMonstersInGame.get(turn).getName() + "'s current stats: " + "\nHealth: " + finalMonstersInGame.get(turn).getHealth() +
                    "\nVictory Points: " + finalMonstersInGame.get(turn).getVictoryPoints() +
                    "\nEnergy Cubes: " + finalMonstersInGame.get(turn).getEnergyCubes() +
                    "\nIn Tokyo: " + finalMonstersInGame.get(turn).getMonsterLocation());

            boolean removeFlag = true;
            for (int i = 0; i < finalMonstersInGame.size(); i++) {
                if (finalMonstersInGame.get(i).getHealth() <= 0) {
                    System.out.println(finalMonstersInGame.get(i).getName() + " has been eliminated");
                    for (int j = 0; j < finalMonstersInGame.get(i).getPlayerDeck().size(); j++) { //I added a fix for adding powercards
                        if (finalMonstersInGame.get(i).getPlayerDeck().get(j).getPowerCardName().equals("It Has a Child")) {
                            System.out.println(finalMonstersInGame.get(i).getName() + " has been revived!");
                            finalMonstersInGame.get(i).getPlayerDeck().clear();
                            finalMonstersInGame.get(i).setVictoryPoints(0);
                            finalMonstersInGame.get(i).setHealth(0);
                            //Re-enter into deck
                            removeFlag = false;
                        }

//                            deck.addPowerCard(finalMonstersInGame.get(i).getPlayerDeck().remove(
//                                    finalMonstersInGame.get(i).getPlayerDeck().get(j))))))))))
                        deck.addPowerCard(finalMonstersInGame.get(i).getPlayerDeck().remove(j)); //I think I fixed the bug
                    }
                    if (removeFlag) {
                        energyPool = energyPool + finalMonstersInGame.get(i).getEnergyCubes();
                        finalMonstersInGame.remove(i);
                    }
                }
            }

            for (int i = 0; i < finalMonstersInGame.size(); i++) {
                System.out.println(finalMonstersInGame.get(i).getName() + " has " + finalMonstersInGame.get(i).getHealth() + " health");
            }

            //If the array list size for monsters is 1 that means the last player is alive and wins
            if (finalMonstersInGame.size() == 1) {
                System.out.println("Congratulations! " + finalMonstersInGame.get(0).getName() + " you won!");
                break;
            }
            if (finalMonstersInGame.get(turn).getVictoryPoints() >= 20) {
                System.out.println("Congratulations! " + finalMonstersInGame.get(0).getName() + " you won!");
                break;
            }

            /* ~ 4. Buy Power Cards ~ */
            // Prompt them to buy power cards. Power cards go into effect immediately
            // 3 face up cards at all times, immediately replaced, buy one at a time

            System.out.println("Entering Power Card Shop!");


            while (true){
                System.out.println("Current Energy cubes: " + finalMonstersInGame.get(turn).getEnergyCubes());
                // Displaying faceUpCards
                for (int i = 0; i < faceUpCards.size(); i++) {
                    System.out.println(i + 1 + ": " + faceUpCards.get(i).getPowerCardName() +
                            ". Cost: " + faceUpCards.get(i).getPowerCardCost() + " Type: " +
                            faceUpCards.get(i).getPowerCardType() + " Effect: " + faceUpCards.get(i).getPowerCardAbility());
                }
                System.out.println("Select a card from the face up pile: 1-3, select 4 to refresh cards for 2 energy, " +
                        "or 5 to exit the shop.");
                // Deal power cards to be face up & display them

                // Check input in range
                int cardSelection = Integer.parseInt(getInput(5));
                switch(cardSelection){
                    case 1:
                        // Buy first card
                        if(finalMonstersInGame.get(turn).getEnergyCubes() >= faceUpCards.get(0).getPowerCardCost()){
                            System.out.println("Buying " + faceUpCards.get(0).getPowerCardName());
                            // Updating energy cubes of player
                            finalMonstersInGame.get(turn).setEnergyCubes(finalMonstersInGame.get(turn).getEnergyCubes()
                                    - faceUpCards.get(0).getPowerCardCost());
                            // Player buys the card, removes from faceUpCards & adds to player's cards arrlist
                            finalMonstersInGame.get(turn).getPlayerDeck().add(faceUpCards.remove(0));
                            // Replacing faceUpCard purchased
                            faceUpCards.add(deck.dealPowerCard());
                        }
                        else{
                            System.out.println("You do not have enough energy cubes.");
                        }
                        break;

                    case 2:
                        // Buy second card
                        if(finalMonstersInGame.get(turn).getEnergyCubes() >= faceUpCards.get(1).getPowerCardCost()){
                            System.out.println("Buying " + faceUpCards.get(1).getPowerCardName());
                            // Updating energy cubes of player
                            finalMonstersInGame.get(turn).setEnergyCubes(finalMonstersInGame.get(turn).getEnergyCubes()
                                    - faceUpCards.get(1).getPowerCardCost());
                            // Player buys the card, removes from faceUpCards & adds to player's cards arrlist
                            finalMonstersInGame.get(turn).getPlayerDeck().add(faceUpCards.remove(1));
                            // Replacing faceUpCard purchased
                            faceUpCards.add(deck.dealPowerCard());
                        }
                        else{
                            System.out.println("You do not have enough energy cubes.");
                        }
                        break;

                    case 3:
                        // Buy third card
                        if(finalMonstersInGame.get(turn).getEnergyCubes() >= faceUpCards.get(2).getPowerCardCost()){
                            System.out.println("Buying " + faceUpCards.get(2).getPowerCardName());
                            // Updating energy cubes of player
                            finalMonstersInGame.get(turn).setEnergyCubes(finalMonstersInGame.get(turn).getEnergyCubes()
                                    - faceUpCards.get(2).getPowerCardCost());
                            // Player buys the card, removes from faceUpCards & adds to player's cards arrlist
                            finalMonstersInGame.get(turn).getPlayerDeck().add(faceUpCards.remove(2));
                            // Replacing faceUpCard purchased
                            faceUpCards.add(deck.dealPowerCard());
                        }
                        else{
                            System.out.println("You do not have enough energy cubes.");
                        }
                        break;

                    case 4:
                        // Refresh faceUpCards, discard faceup cards, and deal 3 more from the deck
                        if(finalMonstersInGame.get(turn).getEnergyCubes() >= 2) {
                            finalMonstersInGame.get(turn).setEnergyCubes(finalMonstersInGame.get(turn).getEnergyCubes() - 2);
                            deck.addPowerCard(faceUpCards.remove(0));
                            deck.addPowerCard(faceUpCards.remove(0));
                            deck.addPowerCard(faceUpCards.remove(0));
                            faceUpCards.add(deck.dealPowerCard());
                            faceUpCards.add(deck.dealPowerCard());
                            faceUpCards.add(deck.dealPowerCard());
                        }
                        else{
                            System.out.println("You do not have enough energy cubes.");
                        }
                        break;

                    case 5:
                        // Quit buying power cards
                        System.out.println("Exiting Power Card shop.");
                        // Breaks out of the while loop when user enters 4
                        break;
                }
                if (cardSelection == 5){
                    // Break out of while loop
                    break;
                }
            }

            // Displaying the player's current PowerCards
            System.out.println("Displaying " + finalMonstersInGame.get(turn).getName() + "\'s kept Power Cards.");
            for (int i = 0; i < finalMonstersInGame.get(turn).getPlayerDeck().size(); i++) {
                System.out.println(finalMonstersInGame.get(turn).getPlayerDeck().get(i).getPowerCardName() + ": " +
                        finalMonstersInGame.get(turn).getPlayerDeck().get(i).getPowerCardAbility());
            }

            // TODO: Hunter
            // Immediately use Discard type cards
            for (int i = 0; i < finalMonstersInGame.get(turn).getPlayerDeck().size(); i++) {
                // Select Discard types & display, then use and discard back to deck
                if (finalMonstersInGame.get(turn).getPlayerDeck().get(i).getPowerCardType().equals("Discard")){
                    System.out.println(finalMonstersInGame.get(turn).getPlayerDeck().get(i).getPowerCardName() + ": " +
                            finalMonstersInGame.get(turn).getPlayerDeck().get(i).getPowerCardAbility());
                    System.out.println("Playing card: " +
                            finalMonstersInGame.get(turn).getPlayerDeck().get(i).getPowerCardName() + "\nEffect: " +
                            finalMonstersInGame.get(turn).getPlayerDeck().get(i).getPowerCardAbility());

                    // Special Case for Frenzy:
                    if (finalMonstersInGame.get(turn).getPlayerDeck().get(i).getPowerCardName().equals("Frenzy")){
                        turn--;
                    }
                    // Lookup table for Discard cards:
                    tempPowerCard(finalMonstersInGame, finalMonstersInGame.get(turn), finalMonstersInGame.get(turn).getPlayerDeck().get(i));
                    // Discard the card back into the main deck
                    deck.addPowerCard(finalMonstersInGame.get(turn).getPlayerDeck().remove(i));
                }
            }

            System.out.println(finalMonstersInGame.get(turn).getName() + "'s current stats: " + "\nHealth: " + finalMonstersInGame.get(turn).getHealth() +
                    "\nVictory Points: " + finalMonstersInGame.get(turn).getVictoryPoints() +
                    "\nEnergy Cubes: " + finalMonstersInGame.get(turn).getEnergyCubes() +
                    "\nIn Tokyo: " + finalMonstersInGame.get(turn).getMonsterLocation());

            //If the array list size for monsters is 1 that means the last player is alive and wins
            if (finalMonstersInGame.size() == 1) {
                System.out.println("Congratulations! " + finalMonstersInGame.get(0).getName() + " you won!");
                break;
            }
            if (finalMonstersInGame.get(turn).getVictoryPoints() >= 20) {
                System.out.println("Congratulations! " + finalMonstersInGame.get(0).getName() + " you won!");
                break;
            }

            // Changing turns
            if (turn == finalMonstersInGame.size() - 1){
                turn = 0;
            }
            else{
                turn++;
            }
            // End of the loop


        }
    }

    // List of all power card abilities
    public static void tempPowerCard(ArrayList<Monster> finalMonstersInGame, Monster player, PowerCard card){
        switch (card.getPowerCardName()){
            case "Amusement Park":
                player.setVictoryPoints(player.getVictoryPoints() + 4);
                break;
            case "Army":
                player.setVictoryPoints(player.getVictoryPoints() + 1);
                for (PowerCard p : player.getPlayerDeck()){
                    player.setHealth(player.getHealth() - 1);
                }
                break;
            case "Apartment Building":
                player.setVictoryPoints(player.getVictoryPoints() + 3);
                break;
            case "Commuter Train":
                player.setVictoryPoints(player.getVictoryPoints() + 2);
                break;
            case "Corner Store":
                player.setVictoryPoints(player.getVictoryPoints() + 1);
                break;
            case "Drop from High Altitude":
                player.setVictoryPoints(player.getVictoryPoints() + 2);
                player.setMonsterLocation(true);
                break;
            case "Energize":
                player.setVictoryPoints(player.getVictoryPoints() + 9);
                break;
            case "Evacuation Orders":
                for (Monster m : finalMonstersInGame) {
                    if (m.getName() != player.getName() && m.getVictoryPoints() > 5) {
                        m.setVictoryPoints(m.getVictoryPoints() - 5);
                    }
                    else{
                        m.setVictoryPoints(0);
                    }
                }
                break;
            case "Fire Blast":
                for (Monster m : finalMonstersInGame) {
                    if (m.getName() != player.getName() && m.getVictoryPoints() > 2){
                        m.setVictoryPoints(m.getVictoryPoints() - 2);
                    }
                    else{
                        m.setVictoryPoints(0);
                    }
                }
                break;
            case "Frenzy":
                // TODO: Take another turn immediately after this card is played
                break;
            case "Gas Refinery":
                player.setVictoryPoints(player.getVictoryPoints() + 2);
                for (Monster m : finalMonstersInGame) {
                    if (m.getName() != player.getName()){
                        m.setHealth(m.getHealth() - 3);
                    }
                }
                break;
            case "Heal":
                player.setHealth(player.getHealth() + 2);
                break;
            case "High Altitude Bombing":
                for (Monster m : finalMonstersInGame) {
                    m.setHealth(m.getHealth() - 3);
                }
                break;
            case "Jet Fighters":
                player.setVictoryPoints(player.getVictoryPoints() + 5);
                player.setHealth(player.getHealth() - 4);
                break;
            case "National Guard":
                player.setVictoryPoints(player.getVictoryPoints() + 2);
                player.setHealth(player.getHealth() - 2);
                break;
            case "Nuclear Power Plant":
                player.setVictoryPoints(player.getVictoryPoints() + 2);
                player.setHealth(player.getHealth() + 3);
                break;
            case "Skyscraper":
                player.setVictoryPoints(player.getVictoryPoints() + 4);
                break;
            case "Tanks":
                player.setVictoryPoints(player.getVictoryPoints() + 4);
                player.setHealth(player.getHealth() - 3);
                break;
            case "Vast Storm":
                player.setVictoryPoints(player.getVictoryPoints() + 2);
                for (Monster m : finalMonstersInGame) {
                    if (m.getName() != player.getName()){
                        m.setEnergyCubes((int) Math.ceil(m.getEnergyCubes() / 2));
                    }
                }
                break;
        }
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

    public static String getInput(int menuRange) {
        Scanner in = new Scanner(System.in);
        String input = "";
        boolean valid = false;
        while (!valid) {  // Validate their monster exists
            input = in.nextLine();
            while (!isNumber(input)) {  // Checks if input is a number
                System.out.println("Please enter a REAL number: ");
                input = in.nextLine();
            }

            if (1 <= Integer.parseInt(input) && Integer.parseInt(input) <= menuRange) {
                valid = true;
            }
            else {
                System.out.println("That option does not exist! Try again: ");
            }
        }
        return input;
    }
}


