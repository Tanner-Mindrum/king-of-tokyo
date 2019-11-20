public class PowerCard {

    private String powerCardName;
    private int powerCardCost;
    private String powerCardType;
    private String powerCardAbility;

    // Overloaded constructor initializes instance variables
    public PowerCard(String powerCardName, int powerCardCost, String powerCardType, String powerCardAbility) {
        this.powerCardName = powerCardName;
        this.powerCardCost = powerCardCost;
        this.powerCardType = powerCardType;
        this.powerCardAbility = powerCardAbility;
    }

    /* This is a basic class composed solely of setters and getters for each of the attributes of a Power Card:
     Name, Cost, Type, Ability */

    public String getPowerCardName() {
        return this.powerCardName;
    }

    public void setPowerCardName(String name) {
        this.powerCardName = name;
    }

    public int getPowerCardCost() {
        return this.powerCardCost;
    }

    public void setPowerCardCost(int cost) {
        this.powerCardCost = cost;
    }

    public String getPowerCardType() {
        return this.powerCardType;
    }

    public void setPowerCardType(String type) {
        this.powerCardType = type;
    }

    private String getPowerCardAbility() {
        return this.powerCardAbility;
    }

    private void setPowerCardAbility(String ability) {
        this.powerCardAbility = ability;
    }
}
