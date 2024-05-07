import java.io.*;

import java.awt.*;

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

public class OneRowNim extends TwoPlayerGame implements CLUIPlayableGame {
    public static final int MAX_PICKUP = 3;
    public static final int MAX_STICKS = 11;

    private int nSticks = MAX_STICKS;

    public OneRowNim() {
    } // Constructors

    public OneRowNim(int sticks) {
        nSticks = sticks;
    }

    public OneRowNim(int sticks, int starter) {
        nSticks = sticks;
        setPlayer(starter);
    }

    public boolean takeSticks(int num) {
        if (num < 1 || num > MAX_PICKUP || num > nSticks)
            return false; // Error
        else // Valid move
        {
            nSticks = nSticks - num;
            return true;
        } // else
    }// takeSticks

    public int getSticks() {
        return nSticks;
    } // getSticks

    public String getRules() {
        return "\n*** The Rules of One Row Nim ***\n" +
                "(1) A number of sticks between 7 and " + MAX_STICKS + " is chosen.\n" +
                "(2) Two players alternate making moves.\n" +
                "(3) A move consists of subtracting between 1 and\n\t" +
                MAX_PICKUP + " sticks from the current number of sticks.\n" +
                "(4) A player who cannot leave a positive\n\t" +
                " number of sticks for the other player loses.\n";
    }

    public boolean gameOver() { /** From TwoPlayerGame */
        return (nSticks <= 0);
    } // gameOver()

    public String getWinner() { /** From TwoPlayerGame */
        if (gameOver()) // {
            return "" + getPlayer() + " Nice game.";
        return "The game is not over yet."; // Game is not over
    } // getWinner()

    public String getGamePrompt() {
        return "\nYou can pick up between 1 and " + Math.min(MAX_PICKUP, nSticks) + " : ";
    }

    public String reportGameState() {
        if (!gameOver())
            return ("\nSticks left: " + getSticks() + " Who's turn: Player " + getPlayer());
        else
            return ("\nSticks left: " + getSticks() + " Game over! Winner is Player " + getWinner() + "\n");
    } // reportGameState()

    /** From CLUIPlayableGame interface */
    public void play(UserInterface ui) {
        int sticks = 0;
        ui.report(getRules());
        if (computer1 != null)
            ui.report("\nPlayer 1 is a " + computer1.toString());
        if (computer2 != null)
            ui.report("\nPlayer 2 is a " + computer2.toString());

        while (!gameOver()) {
            IPlayer computer = null; // Assume no computers playing
            ui.report(reportGameState());
            switch (getPlayer()) {
                case PLAYER_ONE: // Player 1's turn
                    computer = computer1;
                    break;
                case PLAYER_TWO: // Player 2's turn
                    computer = computer2;
                    break;
            } // cases

            if (computer != null) { // If computer's turn
                sticks = Integer.parseInt(computer.makeAMove(""));
                ui.report(computer.toString() + " takes " + sticks + " sticks.\n");
            } else { // otherwise, user's turn
                ui.report(getGamePrompt());
                sticks = Integer.parseInt(ui.getUserInput()); // Get user's move
            }
            if (takeSticks(sticks)) // If a legal move
                changePlayer();
        } // while
        ui.report(reportGameState()); // The game is now over
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

    public static void main(String args[]) {
        KeyboardReader kb = new KeyboardReader(); // Cria um leitor de teclado para entrada de dados
        OneRowNim game = new OneRowNim(); // Inicializa o jogo com o número máximo de palitos

        // Pergunta ao usuário quantos jogadores de computador estão jogando
        kb.report("Bem-vindo ao One Row Nim! Quantos computadores estao jogando (0, 1, ou 2)? ");
        int m = kb.getKeyboardInteger();

        // Pergunta ao usuário contra qual tipo de jogador de computador ele deseja
        // jogar
        kb.report("Deseja jogar contra NimPlayerBad (digite 1) ou NimPlayerSuper (digite 2)? ");
        int playerType = kb.getKeyboardInteger();
        IPlayer computerPlayer = null;

        // Cria o jogador de computador com base na escolha do usuário
        if (playerType == 1) {
            computerPlayer = new NimPlayerBad(game);
            kb.report("Voce escolheu jogar contra NimPlayerBad.\n");
        } else if (playerType == 2) {
            computerPlayer = new NimPlayerSuper(game);
            kb.report("Voce escolheu jogar contra NimPlayerSuper.\n");
        }

        // Adiciona o jogador de computador ao jogo e decide aleatoriamente quem joga
        // primeiro se houver um jogador de computador
        if (m == 1) {
            game.addComputerPlayer(computerPlayer);
            if (Math.random() < 0.5) {
                game.setPlayer(TwoPlayerGame.PLAYER_ONE);
            } else {
                game.setPlayer(TwoPlayerGame.PLAYER_TWO);
            }
        }
        // Adiciona dois jogadores de computador ao jogo se o usuário escolher dois
        // jogadores de computador
        else if (m == 2) {
            game.addComputerPlayer(new NimPlayerBad(game));
            game.addComputerPlayer(new NimPlayerSuper(game));
        }

        // Inicia o jogo
        game.play(kb);
    }

} // OneRowNim class

interface IPlayer {
    public String makeAMove(String prompt);
}

interface IGame {
    public String getGamePrompt();

    public String reportGameState();
} // IGame

interface UserInterface {
    public String getUserInput();

    public void report(String s);

    public void reportWinner(String winner); // New method
}

interface CLUIPlayableGame extends IGame {
    public abstract void play(UserInterface ui);
}

abstract class TwoPlayerGame {
    public static final int PLAYER_ONE = 1; // Class constants
    public static final int PLAYER_TWO = 2;

    protected boolean onePlaysNext = true; // Player 1 plays next
    protected int nComputers = 0; // How many computer players
    protected IPlayer computer1, computer2; // Computers are IPlayers

    public void setPlayer(int starter) {
        if (starter == PLAYER_TWO)
            onePlaysNext = false;
        else
            onePlaysNext = true;
    } // setPlayer()

    public int getPlayer() {
        if (onePlaysNext)
            return PLAYER_ONE;
        else
            return PLAYER_TWO;
    } // getPlayer()

    public void changePlayer() {
        onePlaysNext = !onePlaysNext;
    } // changePlayer

    public int getNComputers() {
        return nComputers;
    }

    public String getRules() {
        return "The rules of this game are: ";
    }

    public void addComputerPlayer(IPlayer player) {
        if (nComputers == 0)
            computer2 = player;
        else if (nComputers == 1)
            computer1 = player;
        else
            return; // No more than 2 players
        ++nComputers;
    }

    public abstract boolean gameOver(); // Abstract Methods

    public abstract String getWinner();
} // TwoPlayerGame

class KeyboardReader implements UserInterface {
    public void reportWinner(String winner) {
        System.out.println(">>>>>>> WINNER is " + winner + " <<<<<<<");
    }

    private BufferedReader reader;

    public KeyboardReader() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public String getKeyboardInput() {
        return readKeyboard();
    }

    public int getKeyboardInteger() {
        return Integer.parseInt(readKeyboard());
    }

    public double getKeyboardDouble() {
        return Double.parseDouble(readKeyboard());
    }

    public String getUserInput() {
        return getKeyboardInput();
    }

    public void report(String s) {
        System.out.print(s);
    }

    public void display(String s) {
        System.out.print(s);
    }

    private String readKeyboard() {
        String line = "";
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }
}

// Esta classe define um jogador chamado NimPlayerSuper que implementa a
// interface IPlayer.
// Representa um jogador no jogo One Row Nim.
class NimPlayerSuper implements IPlayer {
    private OneRowNim game;

    // Construtor que recebe uma instância do jogo OneRowNim como parâmetro.
    public NimPlayerSuper(OneRowNim game) {
        this.game = game;
    }

    // Método que faz uma jogada calculando a jogada ideal com base no número de
    // palitos restantes.
    public String makeAMove(String prompt) {
        int sticksLeft = game.getSticks();
        int idealMove = (sticksLeft - 1) % (OneRowNim.MAX_PICKUP + 1);
        if (idealMove == 0)
            idealMove = 1; // Se a jogada ideal for 0, defina-a como 1 para garantir uma jogada válida.
        return "" + idealMove;
    }
    /*
     * Esta estratégia é boa porque, se o jogador puder sempre deixar um número de
     * palitos que seja um múltiplo do número máximo de palitos que podem ser pegos,
     * ele poderá forçar o outro jogador a pegar o último palito, desde que ambos os
     * jogadores continuem jogando de maneira ideal.
     */

    // Método que retorna uma representação em string do jogador.
    public String toString() {
        return "NimPlayerSuper";
    }
}
