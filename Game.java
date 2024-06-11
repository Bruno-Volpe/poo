public class Game {
    private Room currentRoom;

    public Game() {
        createRooms();
    }

    private void createRooms() {
        Room outside, theatre, pub, lab, office, cellar;

        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        cellar = new Room("in the cellar");

        // initialize room exits
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        theatre.setExit("west", outside);
        pub.setExit("east", outside);
        lab.setExit("north", outside);
        lab.setExit("east", office);
        office.setExit("west", lab);
        office.setExit("down", cellar);
        cellar.setExit("up", office);

        currentRoom = outside;  // start game outside
    }

    /**
     * Prints a welcome message and the current location.
     */
    public void printWelcome() {
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        printLocationInfo();
    }

    /**
     * Prints the current location's information.
     */
    public void printLocationInfo() {
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Simulates going to a new room.
     * @param direction The direction to move.
     */
    public void goRoom(String direction) {
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            currentRoom = nextRoom;
            printLocationInfo();
        }
    }
}
