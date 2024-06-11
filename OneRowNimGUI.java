// Bruno Giacomini Volpe - 14651980
// Guilherme Aureliano Xavier - 14575641

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OneRowNimGUI extends JFrame {
    private OneRowNim game;
    private JTextField sticksInputField;
    private JTextArea gameStateArea;
    private JButton takeSticksButton;
    private JButton resetGameButton;

    public OneRowNimGUI(OneRowNim game) {
        this.game = game; // Pass the game instance

        // Set up the main window
        setTitle("One Row Nim Game");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create main panel components
        gameStateArea = new JTextArea(10, 40);
        gameStateArea.setEditable(false);
        sticksInputField = new JTextField(5);
        takeSticksButton = new JButton("Take Sticks");
        resetGameButton = new JButton("Reset Game");

        // Add action listeners for main panel buttons
        takeSticksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleTakeSticks();
            }
        });

        resetGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        // Layout main panel components
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter sticks to take:"));
        inputPanel.add(sticksInputField);
        inputPanel.add(takeSticksButton);

        JPanel controlPanel = new JPanel();
        controlPanel.add(resetGameButton);

        setLayout(new BorderLayout());
        add(new JScrollPane(gameStateArea), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(controlPanel, BorderLayout.SOUTH);

        // Initialize game state
        updateGameState();
    }

    private void handleTakeSticks() {
        try {
            int sticks = Integer.parseInt(sticksInputField.getText());
            if (game.takeSticks(sticks)) {
                game.changePlayer();
                updateGameState();
                if (game.gameOver()) {
                    gameStateArea.append("\nGame Over! Winner: Player " + game.getWinner() + "\n");
                    takeSticksButton.setEnabled(false);
                }
            } else {
                gameStateArea.append("\nInvalid move. Try again.\n");
            }
        } catch (NumberFormatException ex) {
            gameStateArea.append("\nInvalid input. Enter a number.\n");
        }
    }

    private void resetGame() {
        game.reset(); // Reset the game
        updateGameState();
        takeSticksButton.setEnabled(true);
    }

    private void updateGameState() {
        gameStateArea.setText(game.reportGameState());
        sticksInputField.setText("");
        sticksInputField.requestFocus();
    }

    public static void main(String[] args) {
        int numPlayers = Integer.parseInt(JOptionPane.showInputDialog("Enter number of players (1 or 2):"));
        OneRowNim game = new OneRowNim();
        if (numPlayers == 1) {
            game.addComputerPlayer(new NimPlayerBad(game));
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                OneRowNimGUI gui = new OneRowNimGUI(game);
                gui.setVisible(true);
            }
        });
    }
}

// Implementation of the OneRowNim class
class OneRowNim extends TwoPlayerGame implements CLUIPlayableGame {
    public static final int MAX_PICKUP = 3;
    public static final int MAX_STICKS = 11;

    private int nSticks = MAX_STICKS;

    public OneRowNim() {
    }

    public OneRowNim(int sticks) {
        nSticks = sticks;
    }

    public OneRowNim(int sticks, int starter) {
        nSticks = sticks;
        setPlayer(starter);
    }

    public boolean takeSticks(int num) {
        if (num < 1 || num > MAX_PICKUP || num > nSticks)
            return false;
        else {
            nSticks = nSticks - num;
            return true;
        }
    }

    public int getSticks() {
        return nSticks;
    }

    public void reset() {
        nSticks = MAX_STICKS;
        onePlaysNext = true;
    }

    public String getRules() {
        return "\n*** The Rules of One Row Nim ***\n" +
                "(1) A number of sticks between 7 and " + MAX_STICKS + " is chosen.\n" +
                "(2) Two players alternate making moves.\n" +
                "(3) A move consists of subtracting between 1 and\n\t" +
                MAX_PICKUP + " sticks from the current number of sticks.\n" +
                "(4) A player who cannot leave a positive\n\t" +
                " number of sticks for the other player loses.\n";
    }

    public boolean gameOver() {
        return (nSticks <= 0);
    }

    public String getWinner() {
        if (gameOver())
            return "" + getPlayer();
        return "The game is not over yet.";
    }

    public String getGamePrompt() {
        return "\nYou can pick up between 1 and " + Math.min(MAX_PICKUP, nSticks) + " : ";
    }

    public String reportGameState() {
        if (!gameOver())
            return ("\nSticks left: " + getSticks() + " Who's turn: Player " + getPlayer());
        else
            return ("\nSticks left: " + getSticks() + " Game over! Winner is Player " + getWinner() + "\n");
    }

    public void play(UserInterface ui) {
        // Method not used in GUI
    }

    public String submitUserMove(String theMove) {
        int sticks = Integer.parseInt(theMove);
        if (takeSticks(sticks)) {
            changePlayer();
            if (gameOver()) {
                return reportGameState() + "\nGame won by player" + getWinner() + "\n";
            } else {
                return reportGameState() + getGamePrompt();
            }
        }
        return "\nOops. " + sticks + " is an illegal move." + getGamePrompt();
    }

    public void draw(Graphics g) {
        g.setColor(Color.red);
        for (int k = 1; k <= nSticks; k++) {
            g.drawLine(10 + k * 10, 10, 10 + k * 10, 60);
        }
    }
}

interface IPlayer {
    public String makeAMove(String prompt);
}

interface IGame {
    public String getGamePrompt();

    public String reportGameState();
}

interface UserInterface {
    public String getUserInput();

    public void report(String s);

    public void reportWinner(String winner);
}

interface CLUIPlayableGame extends IGame {
    public void play(UserInterface ui);
}

abstract class TwoPlayerGame {
    public static final int PLAYER_ONE = 1;
    public static final int PLAYER_TWO = 2;

    protected boolean onePlaysNext = true;
    protected int nComputers = 0;
    protected IPlayer computer1, computer2;

    public void setPlayer(int starter) {
        onePlaysNext = (starter == PLAYER_ONE);
    }

    public int getPlayer() {
        return onePlaysNext ? PLAYER_ONE : PLAYER_TWO;
    }

    public void changePlayer() {
        onePlaysNext = !onePlaysNext;
    }

    public int getNComputers() {
        return nComputers;
    }

    public String getRules() {
        return "The rules of this game are: ";
    }

    public void addComputerPlayer(IPlayer player) {
        if (nComputers == 0) {
            computer1 = player;
        } else if (nComputers == 1) {
            computer2 = player;
        }
        nComputers++;
    }

    public abstract boolean gameOver();

    public abstract String getWinner();
}

// Example IPlayer implementations
class NimPlayerBad implements IPlayer {
    private OneRowNim game;

    public NimPlayerBad(OneRowNim game) {
        this.game = game;
    }

    public String makeAMove(String prompt) {
        return "" + randomMove();
    }

    private int randomMove() {
        int sticksLeft = game.getSticks();
        return 1 + (int) (Math.random() * Math.min(sticksLeft, game.MAX_PICKUP));
    }

    public String toString() {
        String className = this.getClass().toString();
        return className.substring(5);
    }
}
