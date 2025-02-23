import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class TicTacToe extends JFrame implements ActionListener {
    private final JButton[][] buttons = new JButton[3][3];
    private final char[][] board = new char[3][3];
    private boolean playerTurn = true;
    private final Difficulty difficulty;

    public enum Difficulty { EASY, INTERMEDIATE, HARD }

    public TicTacToe(Difficulty difficulty) {
        this.difficulty = difficulty;
        setTitle("Tic-Tac-Toe");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        setLayout(new BorderLayout());


        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 60));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(this);
                boardPanel.add(buttons[i][j]);
            }
        }
        add(boardPanel, BorderLayout.CENTER);


        JPanel controlPanel = new JPanel();
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StartScreen();
                dispose();
            }
        });
        controlPanel.add(backButton);
        add(controlPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!playerTurn) return;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (e.getSource() == buttons[i][j] && board[i][j] == ' ') {
                    buttons[i][j].setText("X");
                    board[i][j] = 'X';
                    playerTurn = false;
                    if (checkWinner('X')) {
                        JOptionPane.showMessageDialog(this, "You win!");
                        resetBoard();
                        return;
                    }
                    if (!isBoardFull()) {
                        aiMove();
                    } else {
                        JOptionPane.showMessageDialog(this, "It's a draw!");
                        resetBoard();
                    }
                }
            }
        }
    }

    private void aiMove() {
        int[] move;
        switch (difficulty) {
            case EASY:
                move = getRandomMove();
                break;
            case INTERMEDIATE:
                move = minimaxLimited(0, true, Integer.MIN_VALUE, Integer.MAX_VALUE, 3);
                break;
            case HARD:
            default:
                move = minimax(0, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
                break;
        }
        board[move[1]][move[2]] = 'O';
        buttons[move[1]][move[2]].setText("O");

        if (checkWinner('O')) {
            JOptionPane.showMessageDialog(this, "AI wins!");
            resetBoard();
            return;
        }
        if (!isBoardFull()) {
            playerTurn = true;
        } else {
            JOptionPane.showMessageDialog(this, "It's a draw!");
            resetBoard();
        }
    }

    // Hard level
    private int[] minimax(int depth, boolean isMax, int alpha, int beta) {
        if (checkWinner('X')) return new int[]{-10 + depth, -1, -1};
        if (checkWinner('O')) return new int[]{10 - depth, -1, -1};
        if (isBoardFull()) return new int[]{0, -1, -1};

        int bestScore = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int row = -1, col = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = isMax ? 'O' : 'X';
                    int score = minimax(depth + 1, !isMax, alpha, beta)[0];
                    board[i][j] = ' ';

                    if (isMax) {
                        if (score > bestScore) {
                            bestScore = score;
                            row = i;
                            col = j;
                        }
                        alpha = Math.max(alpha, bestScore);
                    } else {
                        if (score < bestScore) {
                            bestScore = score;
                            row = i;
                            col = j;
                        }
                        beta = Math.min(beta, bestScore);
                    }
                    if (beta <= alpha) break;
                }
            }
        }
        return new int[]{bestScore, row, col};
    }

    // Dette er vores begrænset minmax metode til INTERMEDIATE level
    private int[] minimaxLimited(int depth, boolean isMax, int alpha, int beta, int maxDepth) {
        if (checkWinner('X')) return new int[]{-10 + depth, -1, -1};
        if (checkWinner('O')) return new int[]{10 - depth, -1, -1};
        if (isBoardFull()) return new int[]{0, -1, -1};
        if (depth >= maxDepth) return new int[]{0, -1, -1};

        int bestScore = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int row = -1, col = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = isMax ? 'O' : 'X';
                    int score = minimaxLimited(depth + 1, !isMax, alpha, beta, maxDepth)[0];
                    board[i][j] = ' ';

                    if (isMax) {
                        if (score > bestScore) {
                            bestScore = score;
                            row = i;
                            col = j;
                        }
                        alpha = Math.max(alpha, bestScore);
                    } else {
                        if (score < bestScore) {
                            bestScore = score;
                            row = i;
                            col = j;
                        }
                        beta = Math.min(beta, bestScore);
                    }
                    if (beta <= alpha) break;
                }
            }
        }
        return new int[]{bestScore, row, col};
    }

    // Dette er vores "easy" mode, hvor den vælger random
    private int[] getRandomMove() {
        ArrayList<int[]> availableMoves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    availableMoves.add(new int[]{i, j});
                }
            }
        }
        Random rand = new Random();
        int[] move = availableMoves.get(rand.nextInt(availableMoves.size()));
        return new int[]{0, move[0], move[1]};
    }

    private boolean checkWinner(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true;
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) return true;
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true;
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') return false;
            }
        }
        return true;
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
                buttons[i][j].setText("");
            }
        }
        playerTurn = true;
    }
}
