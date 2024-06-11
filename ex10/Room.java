import java.util.HashMap;

/**
 * Represents a room in the adventure game. A room can have multiple exits to other rooms.
 */
public class Room {
    private String description;
    private HashMap<String, Room> exits; // stores exits of this room.

    public Room(String description) {
        this.description = description;
        exits = new HashMap<String, Room>();
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public String getShortDescription() {
        return description;
    }

    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString();
    }

    private String getExitString() {
        String returnString = "Exits:";
        for(String key : exits.keySet()) {
            returnString += " " + key;
        }
        return returnString;
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }
}
