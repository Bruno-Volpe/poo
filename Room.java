public import java.util.HashMap;

/**
 * Represents a room in the game.
 * Each room has a description and exits leading to other rooms.
 */
public class Room {
    private String description;
    private HashMap<String, Room> exits; // stores exits of this room.

    /**
     * Creates a room with a given description.
     * Initially, it has no exits.
     * @param description The room's description.
     */
    public Room(String description) {
        this.description = description;
        this.exits = new HashMap<>();
    }

    /**
     * Sets an exit for the room.
     * @param direction The direction of the exit.
     * @param neighbor The room in that direction.
     */
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    /**
     * Gets the exit in the given direction.
     * @param direction The direction of the exit.
     * @return The room in that direction.
     */
    public Room getExit(String direction) {
        return exits.get(direction);
    }

    /**
     * Gets a string describing the room's exits.
     * @return A description of the exits.
     */
    public String getExitString() {
        StringBuilder exitString = new StringBuilder("Exits: ");
        for (String direction : exits.keySet()) {
            exitString.append(direction).append(" ");
        }
        return exitString.toString();
    }

    /**
     * Gets a long description of the room, including its exits.
     * @return A detailed description of the room.
     */
    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString();
    }
}
 {
    
}
