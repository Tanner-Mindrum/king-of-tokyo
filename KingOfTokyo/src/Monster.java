public class Monster{ 

    private int health; 
    private String monsterName;
    private boolean monsterLocation;
    private int victoryPoints;

    public Monster() {
        health = 0;
        monsterName = "";
        monsterLocation = false;
        victoryPoints = 0;
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
    
}