import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Dice {
    private final String[] diceElements = {"1", "2", "3", "Energy Cube", "Smash", "Heart"};
    private ArrayList<String> diceRoll;
    Dice() {
        diceRoll = new ArrayList<String>();
    }

    //This function rolls the dice
    public ArrayList<String> rollDice() {
        //I run through the diceElements array and create
        int length = diceElements.length - 1;
        Random randNumber = new Random();
        int newNumber;

        for (int i = 0; i <= length; i++) {
            newNumber = randNumber.nextInt(6);
            diceRoll.add(diceElements[newNumber]);
        }
        return diceRoll;
    }

    public void resolveDice() {
        System.out.println("Which dice do you want to keep?");
        boolean check = true;
        while (check) {

        }
        Scanner in = new Scanner(System.in);

    }

}