package KingOfTokyo;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class PlayerSelection {

    public static void main(String[] args) {
        playerSelector(1);
    }

    /**
     * Character Selection:
     * After main menu selects how many human players there are, each player gets a turn
     * entering their name and selecting a monster avatar.
     * @param numHumanPlayers
     * @return
     */
    public static void playerSelector(int numHumanPlayers) {
        ArrayList<Player> currentPlayers = new ArrayList<Player>();

        for (int i = 0; i < numHumanPlayers; i++){
            String name = JOptionPane.showInputDialog("Enter your username: ");
            String avatar = JOptionPane.showInputDialog("Enter your monster name: ");
            currentPlayers.add(new Player(name, avatar, true));
        }
        //System.out.println(currentPlayers);
        JOptionPane.showMessageDialog(null, currentPlayers);


    }

    public static void AISelector(int numAIPlayers) {
        /*
        Loops through the number of AI players selected and assigns them a name and
        avatar.
         */
        for (int i = 0; i < numAIPlayers; i++){

        }
    }
}
