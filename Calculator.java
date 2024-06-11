import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Guilherme Xavier 14575641
 * Bruno Volpe 14651980
 * A simple calculator application that performs basic arithmetic operations
 * (addition,
 * subtraction, multiplication, and division) on two input numbers. The
 * application includes
 * error handling for non-numeric inputs and division by zero.
 * 
 * Challenges faced: Handling division by zero and validating non-numeric input
 * were critical
 * to ensure the application runs smoothly and provides meaningful feedback to
 * the user.
 */
public class Calculator extends JFrame {

    // Fields for number inputs
    private JTextField num1Field, num2Field;
    // Buttons for arithmetic operations
    private JButton addButton, subButton, mulButton, divButton;
    // Label to display the result
    private JLabel resultLabel;

    public Calculator() {
        // Setting up the main window
        setTitle("Simple Calculator");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        // Setting the background color to gray
        getContentPane().setBackground(Color.BLACK);

        // Creating components
        num1Field = new JTextField();
        num2Field = new JTextField();
        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");

        // Setting the background color of text fields and buttons to white
        num1Field.setBackground(Color.WHITE);
        num2Field.setBackground(Color.WHITE);
        addButton.setBackground(Color.WHITE);
        subButton.setBackground(Color.WHITE);
        mulButton.setBackground(Color.WHITE);
        divButton.setBackground(Color.WHITE);

        // Setting the text color of buttons to black
        addButton.setForeground(Color.BLACK);
        subButton.setForeground(Color.BLACK);
        mulButton.setForeground(Color.BLACK);
        divButton.setForeground(Color.BLACK);

        // Creating the result label and setting the text color to white
        resultLabel = new JLabel("Resultado: ", SwingConstants.CENTER);
        resultLabel.setForeground(Color.WHITE);

        // Adding components to the window
        add(createLabel("Número 1: "));
        add(num1Field);
        add(createLabel("Número 2: "));
        add(num2Field);
        add(resultLabel);

        // Creating a panel for the buttons and setting the background color to black
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(addButton);
        buttonPanel.add(subButton);
        buttonPanel.add(mulButton);
        buttonPanel.add(divButton);
        add(buttonPanel);

        // Adding action listeners to the buttons
        addButton.addActionListener(new OperationListener(num1Field, num2Field, resultLabel));
        subButton.addActionListener(new OperationListener(num1Field, num2Field, resultLabel));
        mulButton.addActionListener(new OperationListener(num1Field, num2Field, resultLabel));
        divButton.addActionListener(new OperationListener(num1Field, num2Field, resultLabel));
    }

    // Helper method to create labels with white text
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(Color.WHITE); // Setting the text color to white
        return label;
    }

    public static void main(String[] args) {
        // Instanciando e tornando a calculadora visível
        Calculator calculator = new Calculator();
        calculator.setVisible(true);
    }
}

// Class to handle action events for the operation buttons
class OperationListener implements ActionListener {
    private JTextField num1Field, num2Field;
    private JLabel resultLabel;

    public OperationListener(JTextField num1Field, JTextField num2Field, JLabel resultLabel) {
        this.num1Field = num1Field;
        this.num2Field = num2Field;
        this.resultLabel = resultLabel;
    }

    public void actionPerformed(ActionEvent e) {
        // Getting the text from the input fields
        String num1Text = num1Field.getText();
        String num2Text = num2Field.getText();
        double num1, num2, result = 0;
        // Getting the operation from the action command
        String operation = e.getActionCommand();

        try {
            // Converting text to numbers
            num1 = Double.parseDouble(num1Text);
            num2 = Double.parseDouble(num2Text);

            // Performing the operation based on the button clicked
            switch (operation) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    // Handling division by zero
                    if (num2 == 0) {
                        throw new ArithmeticException("Divisão por zero");
                    }
                    result = num1 / num2;
                    break;
            }
            // Displaying the result
            resultLabel.setText("Resultado: " + result);
        } catch (NumberFormatException ex) {
            // Handling non-numeric input
            resultLabel.setText("Erro: Entrada não numérica");
        } catch (ArithmeticException ex) {
            // Handling division by zero
            resultLabel.setText("Erro: " + ex.getMessage());
        }
    }
}
