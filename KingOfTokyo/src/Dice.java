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
    public ArrayList<String> rollDice() {
        //I run through the diceElements array and create
        int length = diceElements.length - 1;
        Random randNumber = new Random();
        int newNumber;

        for (int i = 0; i <= length; i++) {
            newNumber = randNumber.nextInt(6);
            dice.add(diceElements[newNumber]);
        }
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