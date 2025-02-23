import javax.swing.*;
import java.awt.*;

public class StartScreen extends JFrame {
    private JRadioButton easyButton;
    private JRadioButton intermediateButton;
    private JRadioButton hardButton;
    private JButton startButton;

    public StartScreen() {
        setTitle("Tic Tac Toe - Start Screen");
        setSize(400, 400);  // Same size as the game window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Main panel with a light background and vertical BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 240, 240));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Title label
        JLabel titleLabel = new JLabel("Choose Difficulty");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(titleLabel);

        // Difficulty selection panel using BoxLayout
        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setBackground(new Color(240, 240, 240));
        difficultyPanel.setLayout(new BoxLayout(difficultyPanel, BoxLayout.Y_AXIS));
        // Remove left/right padding so buttons can be centered
        difficultyPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Create radio buttons for difficulties
        easyButton = new JRadioButton("Easy");
        intermediateButton = new JRadioButton("Intermediate");
        hardButton = new JRadioButton("Hard");
        hardButton.setSelected(true); // Default selection

        // Customize radio button fonts and backgrounds
        Font radioFont = new Font("SansSerif", Font.PLAIN, 18);
        easyButton.setFont(radioFont);
        intermediateButton.setFont(radioFont);
        hardButton.setFont(radioFont);
        easyButton.setBackground(new Color(240, 240, 240));
        intermediateButton.setBackground(new Color(240, 240, 240));
        hardButton.setBackground(new Color(240, 240, 240));

        // Center the radio buttons horizontally
        easyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        intermediateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        hardButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Group the radio buttons
        ButtonGroup group = new ButtonGroup();
        group.add(easyButton);
        group.add(intermediateButton);
        group.add(hardButton);

        // Add radio buttons with spacing
        difficultyPanel.add(easyButton);
        difficultyPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        difficultyPanel.add(intermediateButton);
        difficultyPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        difficultyPanel.add(hardButton);

        // Center the difficulty panel within its container
        difficultyPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(difficultyPanel);

        // Add vertical glue to push the Start button towards the bottom
        mainPanel.add(Box.createVerticalGlue());

        // Start Game button with custom styling
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setBackground(new Color(100, 150, 200));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        startButton.addActionListener(e -> {
            TicTacToe.Difficulty difficulty;
            if (easyButton.isSelected()) {
                difficulty = TicTacToe.Difficulty.EASY;
            } else if (intermediateButton.isSelected()) {
                difficulty = TicTacToe.Difficulty.INTERMEDIATE;
            } else {
                difficulty = TicTacToe.Difficulty.HARD;
            }
            new TicTacToe(difficulty);
            dispose();  // Close the start screen
        });

        mainPanel.add(startButton);
        mainPanel.add(Box.createVerticalStrut(20));

        add(mainPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new StartScreen();
    }
}
