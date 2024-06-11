// Bruno Giacomini Volpe - 14651980
// Gabriel Pietro Leone - 13874729

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class InteractiveColorMixer {

    private JFrame frame;
    private JPanel colorPanel;
    private JSlider redSlider, greenSlider, blueSlider;
    private JLabel redLabel, greenLabel, blueLabel;
    private JButton resetButton;

    public InteractiveColorMixer() {
        // Configurando o JFrame
        frame = new JFrame("Interactive Color Mixer");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Criando um painel para os sliders
        JPanel slidersPanel = new JPanel();
        slidersPanel.setLayout(new GridLayout(4, 2));

        // Criando os sliders e rótulos
        redSlider = createSlider("Red");
        redLabel = new JLabel("Red: 128");

        greenSlider = createSlider("Green");
        greenLabel = new JLabel("Green: 128");

        blueSlider = createSlider("Blue");
        blueLabel = new JLabel("Blue: 128");

        // Adicionando os sliders e rótulos ao painel
        slidersPanel.add(redLabel);
        slidersPanel.add(redSlider);
        slidersPanel.add(greenLabel);
        slidersPanel.add(greenSlider);
        slidersPanel.add(blueLabel);
        slidersPanel.add(blueSlider);

        // Criando e adicionando o botão de reset ao painel
        resetButton = new JButton("Reset");
        slidersPanel.add(new JLabel("")); // Espaço vazio para alinhar o botão
        slidersPanel.add(resetButton);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                redSlider.setValue(128);
                greenSlider.setValue(128);
                blueSlider.setValue(128);
            }
        });

        // Adicionando o painel de sliders ao topo do JFrame
        frame.add(slidersPanel, BorderLayout.NORTH);

        // Criando o painel de cor
        colorPanel = new JPanel();
        colorPanel.setBackground(new Color(128, 128, 128));
        colorPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("Mouse entrou no painel de cor.");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("Mouse saiu do painel de cor.");
            }
        });

        // Adicionando o listener para detectar movimentos do mouse dentro do painel de
        // cor
        colorPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                System.out.println("Posição do mouse: X=" + e.getX() + " Y=" + e.getY());
            }
        });

        frame.add(colorPanel, BorderLayout.CENTER);

        // Tornando o JFrame visível
        frame.setVisible(true);
    }

    // Método para criar um JSlider com um ChangeListener
    private JSlider createSlider(String name) {
        JSlider slider = new JSlider(0, 255, 128);
        slider.setName(name);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                System.out.println("Slider movido: " + source.getName() + ", Novo valor: " + source.getValue());
                updateColor();
            }
        });
        return slider;
    }

    // Método para atualizar a cor do painel com base nos valores dos sliders e
    // atualizar os rótulos
    private void updateColor() {
        int red = redSlider.getValue();
        int green = greenSlider.getValue();
        int blue = blueSlider.getValue();
        colorPanel.setBackground(new Color(red, green, blue));
        redLabel.setText("Red: " + red);
        greenLabel.setText("Green: " + green);
        blueLabel.setText("Blue: " + blue);
    }

    // Método principal para iniciar o aplicativo
    public static void main(String[] args) {
        new InteractiveColorMixer();
    }
}
