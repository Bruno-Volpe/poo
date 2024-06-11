/**
 * Contains the valid command words for the game.
 */
public class CommandWords {
    private static final String[] validCommands = {
        "go", "quit", "help"
    };

    public CommandWords() {
        // nothing to do at the moment...
    }

    public String getCommandWord(String commandWord) {
        for (String command : validCommands) {
            if (command.equals(commandWord)) {
                return command;
            }
        }
        return null;
    }

    public boolean isCommand(String aString) {
        for (int i = 0; i < validCommands.length; i++) {
            if (validCommands[i].equals(aString)) {
                return true;
            }
        }
        return false;
    }

    public void showAll() {
        for (String command : validCommands) {
            System.out.print(command + " ");
        }
        System.out.println();
    }
}
