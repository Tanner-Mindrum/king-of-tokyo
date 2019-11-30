import java.lang.reflect.Array;
import java.util.ArrayList;

public class Monster{

    private int health; 
    private String monsterName;
    private boolean monsterLocation;
    private int victoryPoints;
    private int energyCubes;
    private ArrayList<PowerCard> playerDeck;

    public Monster() {
        health = 10;
        monsterName = "";
        monsterLocation = false;
        victoryPoints = 0;
        energyCubes = 100;
        playerDeck = new ArrayList<>();
    }
    
    //Settings the health of the monster 
    public void setHealth(int health) {
        this.health = health;
    }

    //Returning the health of the monster
    public int getHealth() {
        return this.health;
    }
    
    //Setting the name of the monster
    public void setName(String name) {
        monsterName = name;
    }

    public String getName() {
        return monsterName;
    }

    public void setMonsterLocation(boolean location) {
        monsterLocation = location; 
    }


    public boolean getMonsterLocation() {
        return monsterLocation;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }


    public void setEnergyCubes(int energyCubes) {
        this.energyCubes = energyCubes;
    }

    public int getEnergyCubes() {
        return energyCubes;
    }

    public void setPlayerDeck(ArrayList<PowerCard> playerDeck) {
        this.playerDeck = playerDeck;
    }

    public ArrayList<PowerCard> getPlayerDeck() {
        return playerDeck;
    }


//    public ArrayList<String> getMonsterList() {
//        ArrayList<String> monsters = new ArrayList<String>();
//        monsters.add("Alienoid");
//        monsters.add("Cyber Bunny");
//        monsters.add("Giga Zaur");
//        monsters.add("Kraken");
//        monsters.add("Meka Dragon");
//        monsters.add("The King");
//        return monsters;
//    }
    
}