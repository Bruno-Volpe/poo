import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class OneRowNim {
    public static final int MAX_PICKUP = 3; // Número máximo de palitos que podem ser retirados em uma jogada
    public static final int MAX_STICKS = 11; // Número máximo de palitos no jogo

    private int nSticks = MAX_STICKS; // Número atual de palitos no jogo
    private boolean playerOneTurn = true; // Indica se é a vez do jogador um

    // Método para retirar palitos do jogo
    public boolean takeSticks(int num) {
        if (num < 1 || num > MAX_PICKUP || num > nSticks) { // Verifica se o número de palitos a ser retirado é válido
            return false;
        } else {
            nSticks -= num;
            playerOneTurn = !playerOneTurn; // Alterna a vez do jogador
            return true; // Retorna verdadeiro se a jogada for válida
        }
    }

    public int getSticks() {
        return nSticks;
    }

    public boolean gameOver() {
        return nSticks <= 0; // Retorna verdadeiro se não houver mais palitos no jogo
    }

    // Método para obter o vencedor do jogo
    public String getWinner() {
        return playerOneTurn ? "Player Two" : "Player One"; // Retorna o nome do jogador vencedor
    }

    // Método para obter o estado atual do jogo
    public String getGameState() {
        if (gameOver()) { // Verifica se o jogo acabou
            return "Game over! Winner is " + getWinner();
        } else {
            return "Sticks left: " + nSticks + ". " + (playerOneTurn ? "Player One's turn." : "Player Two's turn.");
        }
    }

    // Método para reiniciar o jogo
    public void resetGame() {
        nSticks = MAX_STICKS; // Reseta o número de palitos para o máximo
        playerOneTurn = true; // Define a vez do jogador um
    }
}

// Classe OneRowNimGUI que implementa a interface gráfica do jogo
public class OneRowNimGUI extends JFrame implements ActionListener {
    private OneRowNim game;
    private JTextField userInput;
    private JTextArea gameDisplay;
    private JButton takeSticksButton, resetButton;

    // Construtor da classe
    public OneRowNimGUI() {
        game = new OneRowNim(); // Cria uma instância do jogo
        setTitle("One Row Nim");
        setSize(400, 300);
        setLayout(new BorderLayout()); // Define o layout da janela como BorderLayout

        gameDisplay = new JTextArea();
        add(new JScrollPane(gameDisplay), BorderLayout.CENTER); // Adiciona a área de texto com barra de rolagem ao
                                                                // centro da janela

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel promptLabel = new JLabel("Enter number of sticks to take:");
        inputPanel.add(promptLabel);

        userInput = new JTextField(5);
        inputPanel.add(userInput);

        takeSticksButton = new JButton("Take Sticks");
        takeSticksButton.addActionListener(this); // Listenner, igual JS
        inputPanel.add(takeSticksButton);

        resetButton = new JButton("Reset Game");
        resetButton.addActionListener(this); // Listenner, igual JS
        inputPanel.add(resetButton);

        add(inputPanel, BorderLayout.SOUTH); // Adiciona o painel de entrada na parte inferior da janela

        gameDisplay.setText(game.getGameState()); // Exibe o estado inicial do jogo na área de texto
    }

    // Método para lidar com eventos de ação - como se fosse o onClick do JS
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == takeSticksButton) { // Verifica se o evento foi disparado pelo botão de retirar palitos
            String input = userInput.getText(); // Obtém o texto digitado pelo usuário, como se fosse o value do JS
            try {
                int sticks = Integer.parseInt(input); // Converte o texto em um número inteiro
                if (game.takeSticks(sticks)) { // Verifica se a jogada é válida
                    gameDisplay.setText(game.getGameState()); // Como se fosse o innerHTML do JS
                } else {
                    gameDisplay.setText("Invalid move. Try again.\n" + game.getGameState());
                }
            } catch (NumberFormatException ex) {
                gameDisplay.setText("Please enter a valid number.\n" + game.getGameState());
            }
        } else if (e.getSource() == resetButton) { // Verifica se o evento foi disparado pelo botão de reiniciar o jogo
            game.resetGame(); // Reinicia o jogo
            gameDisplay.setText(game.getGameState()); // Atualiza a área de texto com o novo estado do jogo
        }
        userInput.setText(""); // Limpa o campo de entrada
    }

    // Método principal para iniciar o jogo
    public static void main(String[] args) {
        OneRowNimGUI frame = new OneRowNimGUI(); // Cria uma instância da classe OneRowNimGUI
        frame.setVisible(true); // Torna a janela visível
    }
}
