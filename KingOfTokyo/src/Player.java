package KingOfTokyo;

public class Player {

    private String userName;
    private String avatar;
    // Determines whether player is human or AI, true if human
    private boolean isHumanPlayer;

    public Player(String userName, String avatar, boolean isHumanPlayer){
        this.userName = userName;
        this.avatar = avatar;
        this.isHumanPlayer = isHumanPlayer;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isHumanPlayer() {
        return isHumanPlayer;
    }

    public void setHumanPlayer(boolean humanPlayer) {
        isHumanPlayer = humanPlayer;
    }

    public String toString() {
        return "Name: " + userName + " Avatar: " + avatar + " Is Human: " + isHumanPlayer;
    }
}
