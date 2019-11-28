import java.util.ArrayList;
import java.util.Random;

public class Dice {

    private final String[] diceElements = {"1", "2", "3", "Energy Cube", "Smash", "Heart"};
    private ArrayList<String> dice;
    private int count;
    Dice() {
        dice = new ArrayList<String>();
        count = 0;
    }

    //This function rolls the dice
    public void rollDice() {
        //I run through the diceElements array and create
        int length = diceElements.length - 1;
        Random randNumber = new Random();
        int newNumber;

        for (int i = 0; i <= length; i++) {
            newNumber = randNumber.nextInt(6);
            dice.add(diceElements[newNumber]);
        }
    }

    // Rolls dice when number of black dice being rolled isn't equal to 6
    public void rollModifiedDice(ArrayList<String> modifiedDice) {
        Random randNum = new Random();
        for (String s : modifiedDice) {
            dice.add(modifiedDice.get(randNum.nextInt(modifiedDice.size())));
        }
    }

    public void clearDice() {
        dice.clear();
    }

    public ArrayList<String> returnDice() {
        return dice;
    }

//    //This will remove any dice from the roll if the user wants to reroll
//    public void resolveDice(int removeRoll) {
//        count++;
//
//        if (count != 3) {
//            dice.remove(removeRoll - 1);
//
//        }
//        else {
//            System.out.println("You cannot reroll anymore dice");
//        }
//
//
//    }

}