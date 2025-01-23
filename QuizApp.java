import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class QuizApp {
    private JFrame frame;
    private JPanel panel;
    private JLabel questionLabel, timerLabel;
    private JButton[] optionButtons;
    private JButton submitButton;
    private int currentQuestion = 0;
    private int score = 0;
    private int timer = 15;  // 15 seconds per question
    private Timer countdownTimer;

    // List to track answers (Correct/Incorrect for each question)
    private List<Boolean> answersStatus = new ArrayList<>();
    
    // Java-related quiz questions (moderate level)
    private Question[] questions = {
        new Question("What is the default value of a boolean variable in Java?", new String[]{"true", "false", "0", "null"}, "false"),
        new Question("Which keyword is used to define a class in Java?", new String[]{"class", "struct", "def", "function"}, "class"),
        new Question("Which method is the entry point for a Java application?", new String[]{"main()", "start()", "run()", "init()"}, "main()"),
        new Question("Which of these is used for single-line comments in Java?", new String[]{"//", "/*", "*/", "#"}, "//"),
        new Question("Which class is used to read input from the user in Java?", new String[]{"Scanner", "InputStream", "BufferedReader", "Console"}, "Scanner"),
        new Question("Which of the following is a wrapper class for int in Java?", new String[]{"Integer", "Int", "Double", "Character"}, "Integer"),
        new Question("What is the size of the `char` data type in Java?", new String[]{"1 byte", "2 bytes", "4 bytes", "8 bytes"}, "2 bytes"),
        new Question("Which exception is thrown when dividing by zero in Java?", new String[]{"ArithmeticException", "NullPointerException", "IndexOutOfBoundsException", "IOException"}, "ArithmeticException"),
        new Question("Which keyword is used to create a new object in Java?", new String[]{"new", "create", "object", "class"}, "new"),
        new Question("Which of these methods is used to start a thread in Java?", new String[]{"start()", "begin()", "init()", "run()"}, "start()")
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizApp().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("Java Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null); // Center the window

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(238, 238, 238));  // Light background color

        // Question label with margin
        questionLabel = new JLabel("", JLabel.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        questionLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));  // Added margin (top, left, bottom, right)
        panel.add(questionLabel);

        // Timer label
        timerLabel = new JLabel("Time left: 15s", JLabel.CENTER);
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(timerLabel);

        // Panel for options
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Horizontal alignment with padding

        optionButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton("");
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 14));
            optionButtons[i].setBackground(new Color(240, 240, 240));
            optionButtons[i].setFocusPainted(false);
            optionsPanel.add(optionButtons[i]);
            optionButtons[i].addActionListener(new OptionButtonListener(i));
        }
        panel.add(optionsPanel);

        // Submit button below the options (with margin)
        JPanel submitPanel = new JPanel();  // Create a panel to hold the submit button
        submitPanel.setLayout(new FlowLayout(FlowLayout.CENTER));  // Center the submit button
        submitButton = new JButton("Submit Answer");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 16));
        submitButton.setBackground(new Color(50, 150, 250));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setEnabled(false);
        submitButton.addActionListener(e -> endQuestion());
        submitPanel.add(submitButton); // Add the button to the submitPanel
        panel.add(submitPanel);  // Add the submitPanel to the main panel

        frame.add(panel);
        frame.setVisible(true);

        updateQuestion();
    }

    private void updateQuestion() {
        if (currentQuestion < questions.length) {
            Question current = questions[currentQuestion];

            // Update question and options
            questionLabel.setText(current.getQuestion());
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText(current.getOptions()[i]);
                optionButtons[i].setEnabled(true);
                optionButtons[i].setBackground(new Color(240, 240, 240)); // Reset background color
            }

            submitButton.setEnabled(true);
            timer = 15; // Reset the timer for this question
            timerLabel.setText("Time left: " + timer + "s");

            countdownTimer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (timer > 0) {
                        timer--;
                        timerLabel.setText("Time left: " + timer + "s");
                    } else {
                        endQuestion();
                    }
                }
            });
            countdownTimer.start();
        } else {
            showResult();
        }
    }

    private void endQuestion() {
        countdownTimer.stop();
        for (JButton btn : optionButtons) {
            btn.setEnabled(false);
        }
        submitButton.setEnabled(false);

        // Check if the selected answer is correct
        Question current = questions[currentQuestion];
        if (current.getCorrectAnswer().equals(getSelectedAnswer())) {
            score++;
            answersStatus.add(true); // Correct answer
        } else {
            answersStatus.add(false); // Incorrect answer
        }

        // Wait for 1 second and move to the next question
        Timer delayTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentQuestion++;
                updateQuestion();
            }
        });
        delayTimer.setRepeats(false);
        delayTimer.start();
    }

    private String getSelectedAnswer() {
        for (JButton button : optionButtons) {
            if (button.getBackground() == Color.YELLOW) {
                return button.getText();
            }
        }
        return "";
    }

    private void showResult() {
        StringBuilder summary = new StringBuilder();
        summary.append("Quiz finished! Your score: ").append(score).append("/").append(questions.length).append("\n\n");

        // Add summary of correct/incorrect answers
        for (int i = 0; i < questions.length; i++) {
            Question current = questions[i];
            summary.append("Q").append(i + 1).append(": ").append(current.getQuestion()).append("\n");
            summary.append("Your answer: ").append(current.getCorrectAnswer()).append("\n");
            if (answersStatus.get(i)) {
                summary.append("Correct!\n\n");
            } else {
                summary.append("Incorrect!\n\n");
            }
        }

        JOptionPane.showMessageDialog(frame, summary.toString(), "Quiz Result", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private class OptionButtonListener implements ActionListener {
        private int optionIndex;

        public OptionButtonListener(int optionIndex) {
            this.optionIndex = optionIndex;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            for (JButton btn : optionButtons) {
                btn.setBackground(new Color(240, 240, 240));  // Reset previous selections
            }
            optionButtons[optionIndex].setBackground(Color.YELLOW);  // Highlight the selected option
        }
    }

    private static class Question {
        private String question;
        private String[] options;
        private String correctAnswer;

        public Question(String question, String[] options, String correctAnswer) {
            this.question = question;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }

        public String getQuestion() {
            return question;
        }

        public String[] getOptions() {
            return options;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }
    }
}
