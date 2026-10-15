import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.StyledDocument;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

/**
 * Creates the main application window and wires together the UI components.
 * TypingFrame is responsible for building the Swing layout and handling
 * user interactions by delegating work to helper classes.
 */
public class TypingFrame extends JFrame {
    private static final Color BACKGROUND_COLOR = Color.decode("#2B2B2B");
    private static final Color PANEL_COLOR = Color.decode("#323437");
    private static final Color LABEL_COLOR = Color.decode("#00FF");
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BORDER_COLOR = Color.decode("#44474A");
    private static final Color BUTTON_COLOR = Color.decode("#44474A");
    private static final Color BUTTON_HOVER_COLOR = Color.decode("#5A5D61");
    private static final Font CONTENT_FONT = new Font("Monospaced", Font.PLAIN, 18);
    private static final Font TITLE_FONT = new Font("Monospaced", Font.BOLD, 32);

    private JLabel titleLabel;
    private JLabel subtitleLabel;
    private JLabel timerLabel;
    private JLabel wpmLabel;
    private JLabel accuracyLabel;
    private JLabel correctLabel;
    private JLabel errorLabel;
    private JLabel remainingLabel;
    private JTextPane paragraphPane;
    private JTextArea typingArea;
    private JButton startButton;
    private JButton restartButton;
    private TypingHighlighter typingHighlighter;
    private String currentParagraph;

    private JPanel typingAreaPanel;
    private JPanel resultsPanel;

    private final TextGenerator textGenerator;
    private final TimerManager timerManager;
    private final Calculator calculator;
    private long startTime;

    public TypingFrame() {
        textGenerator = new TextGenerator();
        timerManager = new TimerManager();
        calculator = new Calculator();

        setTitle("Typing Speed Tester");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 700);
        setLocationRelativeTo(null);

        initializeComponents();
        layoutComponents();
        applyDarkTheme();
        configureActions();

        setVisible(true);
    }

    private void initializeComponents() {
        titleLabel = new JLabel("Typing Speed Tester", JLabel.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        subtitleLabel = new JLabel("Practice your speed with a clean typing challenge", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));
        subtitleLabel.setForeground(TEXT_COLOR);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));

        timerLabel = createStatLabel("Time: 00:00");
        wpmLabel = createStatLabel("WPM: 0");
        accuracyLabel = createStatLabel("Accuracy: 0%");
        correctLabel = createStatLabel("Correct: 0");
        errorLabel = createStatLabel("Incorrect: 0");
        remainingLabel = createStatLabel("Remaining: 0");

        paragraphPane = new JTextPane();
        paragraphPane.setEditable(false);
        paragraphPane.setPreferredSize(new Dimension(860, 180));
        paragraphPane.setText("Click Start to generate a paragraph.");
        paragraphPane.setFont(CONTENT_FONT);
        paragraphPane.setOpaque(true);
        paragraphPane.setBackground(PANEL_COLOR);
        paragraphPane.setForeground(TEXT_COLOR);
        paragraphPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2));
        paragraphPane.setMargin(new Insets(12, 12, 12, 12));

        typingArea = new JTextArea();
        typingArea.setLineWrap(true);
        typingArea.setWrapStyleWord(true);
        typingArea.setRows(5);
        typingArea.setEnabled(false);
        typingArea.setFont(CONTENT_FONT);
        typingArea.setBackground(PANEL_COLOR);
        typingArea.setForeground(TEXT_COLOR);
        typingArea.setCaretColor(Color.decode("#E2B714"));
        typingArea.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2));
        typingArea.setMargin(new Insets(12, 12, 12, 12));
        typingArea.setToolTipText("Type the paragraph above. Correct letters are yellow, incorrect letters are red.");

        startButton = new JButton("Start");
        restartButton = new JButton("Restart");
        restartButton.setEnabled(false);
        startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        restartButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startButton.setOpaque(true);
        restartButton.setOpaque(true);
        startButton.setBorderPainted(false);
        restartButton.setBorderPainted(false);
        startButton.setFocusPainted(false);
        restartButton.setFocusPainted(false);
        startButton.setFont(new Font("Monospaced", Font.BOLD, 24));
        restartButton.setFont(CONTENT_FONT);
        startButton.setPreferredSize(new Dimension(150, 60));
        startButton.setMaximumSize(new Dimension(150, 60));
        startButton.setToolTipText("Begin a new typing test.");
        restartButton.setToolTipText("Restart the typing test immediately.");

        setBackground(BACKGROUND_COLOR);
    }

    private void applyDarkTheme() {
        getContentPane().setBackground(BACKGROUND_COLOR);
        titleLabel.setForeground(TEXT_COLOR);
        timerLabel.setForeground(LABEL_COLOR);
        wpmLabel.setForeground(LABEL_COLOR);
        accuracyLabel.setForeground(LABEL_COLOR);
        errorLabel.setForeground(LABEL_COLOR);
        remainingLabel.setForeground(LABEL_COLOR);

        startButton.setBackground(BUTTON_COLOR);
        startButton.setForeground(LABEL_COLOR);
        restartButton.setBackground(BUTTON_COLOR);
        restartButton.setForeground(LABEL_COLOR);

        Border buttonBorder = BorderFactory.createLineBorder(BORDER_COLOR, 1);
        startButton.setBorder(buttonBorder);
        restartButton.setBorder(buttonBorder);

        configureButtonHover(startButton);
        configureButtonHover(restartButton);

        paragraphPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 2),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        typingArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 2),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
    }

    private void layoutComponents() {
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(contentPanel);

        // TOP: Title + Start Button
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        
        JPanel titleTextPanel = new JPanel();
        titleTextPanel.setLayout(new BoxLayout(titleTextPanel, BoxLayout.Y_AXIS));
        titleTextPanel.setOpaque(false);
        titleTextPanel.add(titleLabel);
        titleTextPanel.add(subtitleLabel);
        
        titlePanel.add(titleTextPanel, BorderLayout.CENTER);
        titlePanel.add(startButton, BorderLayout.EAST);

        contentPanel.add(titlePanel, BorderLayout.NORTH);
        contentPanel.add(createCenterPanel(), BorderLayout.CENTER);
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        centerPanel.add(createStatsPanel());
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(createParagraphPanel());
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Create a container for typing area and results (they swap places)
        typingAreaPanel = createTypingAreaPanel();
        resultsPanel = createResultsPanel();
        
        centerPanel.add(typingAreaPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(createButtonsPanel());

        return centerPanel;
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new BorderLayout(10, 10));
        statsPanel.setOpaque(false);

        JPanel topStats = new JPanel();
        topStats.setOpaque(false);
        topStats.setLayout(new BoxLayout(topStats, BoxLayout.X_AXIS));
        topStats.add(timerLabel);
        topStats.add(Box.createRigidArea(new Dimension(20, 0)));
        topStats.add(wpmLabel);
        topStats.add(Box.createRigidArea(new Dimension(20, 0)));
        topStats.add(accuracyLabel);

        JPanel bottomStats = new JPanel();
        bottomStats.setOpaque(false);
        bottomStats.setLayout(new BoxLayout(bottomStats, BoxLayout.X_AXIS));
        bottomStats.add(correctLabel);
        bottomStats.add(Box.createRigidArea(new Dimension(20, 0)));
        bottomStats.add(errorLabel);
        bottomStats.add(Box.createRigidArea(new Dimension(20, 0)));
        bottomStats.add(remainingLabel);

        statsPanel.add(topStats, BorderLayout.NORTH);
        statsPanel.add(bottomStats, BorderLayout.SOUTH);
        return statsPanel;
    }

    private JPanel createParagraphPanel() {
        JPanel paragraphPanel = new JPanel(new BorderLayout());
        paragraphPanel.setBackground(PANEL_COLOR);
        javax.swing.border.TitledBorder paragraphBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(BORDER_COLOR), "Target Paragraph");
        paragraphBorder.setTitleColor(TEXT_COLOR);
        paragraphPanel.setBorder(paragraphBorder);
        JScrollPane scrollPane = new JScrollPane(paragraphPane);
        scrollPane.setPreferredSize(new Dimension(860, 180));
        scrollPane.getViewport().setBackground(PANEL_COLOR);
        paragraphPanel.add(scrollPane, BorderLayout.CENTER);
        return paragraphPanel;
    }

    private JPanel createTypingAreaPanel() {
        JPanel typingPanel = new JPanel(new BorderLayout());
        typingPanel.setBackground(PANEL_COLOR);
        javax.swing.border.TitledBorder typingBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(BORDER_COLOR), "Type Here");
        typingBorder.setTitleColor(TEXT_COLOR);
        typingPanel.setBorder(typingBorder);
        JScrollPane scrollPane = new JScrollPane(typingArea);
        scrollPane.setPreferredSize(new Dimension(860, 120));
        scrollPane.getViewport().setBackground(PANEL_COLOR);
        typingPanel.add(scrollPane, BorderLayout.CENTER);
        return typingPanel;
    }

    private JPanel createResultsPanel() {
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBackground(PANEL_COLOR);
        resultsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.decode("#E2B714"), 3),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        resultsPanel.setPreferredSize(new Dimension(860, 120));
        resultsPanel.setVisible(false);

        // Results title
        JLabel resultsTitle = new JLabel("RESULTS");
        resultsTitle.setFont(new Font("Monospaced", Font.BOLD, 40));
        resultsTitle.setForeground(Color.decode("#E2B714"));
        resultsTitle.setAlignmentX(CENTER_ALIGNMENT);
        resultsPanel.add(resultsTitle);
        resultsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // TIME DISPLAY - PROMINENT AND LARGE
        JLabel resultTimeLabel = new JLabel("Time: 00:00");
        resultTimeLabel.setFont(new Font("Monospaced", Font.BOLD, 48));
        resultTimeLabel.setForeground(Color.decode("#E2B714"));
        resultTimeLabel.setAlignmentX(CENTER_ALIGNMENT);
        resultsPanel.add(resultTimeLabel);
        resultsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Create result stats labels
        JLabel resultWpmLabel = new JLabel("WPM: 0");
        JLabel resultAccuracyLabel = new JLabel("Accuracy: 0%");
        JLabel resultCorrectLabel = new JLabel("✓ Correct: 0");
        JLabel resultErrorLabel = new JLabel("✗ Incorrect: 0");

        Font resultFont = new Font("Monospaced", Font.PLAIN, 32);
        Color resultColor = Color.decode("#D8D8D8");

        for (JLabel label : new JLabel[]{resultWpmLabel, resultAccuracyLabel, resultCorrectLabel, resultErrorLabel}) {
            label.setFont(resultFont);
            label.setForeground(resultColor);
            label.setAlignmentX(CENTER_ALIGNMENT);
            resultsPanel.add(label);
            resultsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        // Store references for updating
        resultsPanel.putClientProperty("timeLabel", resultTimeLabel);
        resultsPanel.putClientProperty("wpmLabel", resultWpmLabel);
        resultsPanel.putClientProperty("accuracyLabel", resultAccuracyLabel);
        resultsPanel.putClientProperty("correctLabel", resultCorrectLabel);
        resultsPanel.putClientProperty("errorLabel", resultErrorLabel);

        return resultsPanel;
    }

    private JPanel createButtonsPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(restartButton);
        buttonPanel.add(Box.createHorizontalGlue());
        return buttonPanel;
    }

    private JLabel createStatLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(CONTENT_FONT);
        label.setForeground(LABEL_COLOR);
        label.setPreferredSize(new Dimension(260, 30));
        return label;
    }

    private void configureActions() {
        // Timer listener for updating elapsed time display during test
        timerManager.setListener(new TimerManager.TimerUpdateListener() {
            @Override
            public void onTimeChanged(int secondsRemaining) {
                // Update timer to show ELAPSED time, not remaining
                updateTimerDisplay();
                updateTypingStats();
            }

            @Override
            public void onTimerFinished() {
                finishTest();
            }
        });

        typingArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateHighlightOnly();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateHighlightOnly();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateHighlightOnly();
            }
        });

        startButton.addActionListener(action -> startTest());
        restartButton.addActionListener(action -> restartTest());
    }

    private void updateHighlightOnly() {
        String typedText = typingArea.getText();

        if (typingHighlighter != null) {
            typingHighlighter.updateHighlight(typedText);
        }
    }

    private void startTest() {
        resetAndBeginTest();
    }

    private void restartTest() {
        timerManager.stop();
        resetAndBeginTest();
    }

    private void resetAndBeginTest() {
        try {
            // Hide results panel, show typing area
            resultsPanel.setVisible(false);
            typingAreaPanel.setVisible(true);

            // Exception handling for paragraph generation
            try {
                currentParagraph = textGenerator.getRandomParagraph();
                if (currentParagraph == null || currentParagraph.isEmpty()) {
                    throw new IllegalStateException("No paragraph available");
                }
            } catch (Exception e) {
                currentParagraph = "Error loading paragraph. Please try again.";
                System.err.println("Error getting paragraph: " + e.getMessage());
            }

            paragraphPane.setText(currentParagraph);
            typingHighlighter = new TypingHighlighter((StyledDocument) paragraphPane.getDocument(), currentParagraph);
            typingHighlighter.applyDefaultStyle();

            typingArea.setText("");
            typingArea.setEnabled(true);
            typingArea.requestFocusInWindow();
            updateStats(0, 0, 0, 0, currentParagraph.length());
            startButton.setEnabled(false);
            restartButton.setEnabled(true);

            updateTimerDisplay();  // Show 00:00 at start

            timerManager.stop();
            startTime = System.currentTimeMillis();
            timerManager.start(60);  // 60 second limit
            
        } catch (Exception e) {
            System.err.println("Critical error in resetAndBeginTest: " + e.getMessage());
            e.printStackTrace();
            startButton.setEnabled(true);
            restartButton.setEnabled(false);
        }
    }

    private void finishTest() {
        try {
            timerManager.stop();
            typingArea.setEnabled(false);
            startButton.setEnabled(true);
            restartButton.setEnabled(true);

            // Calculate actual elapsed time
            int elapsedSeconds = (int)((System.currentTimeMillis() - startTime) / 1000);
            
            // Ensure at least 1 second to avoid division by zero in WPM calculation
            if (elapsedSeconds < 1) {
                elapsedSeconds = 1;
            }
            
            int minutes = elapsedSeconds / 60;
            int seconds = elapsedSeconds % 60;

            // Exception handling for calculator
            Result finalResult = null;
            try {
                finalResult = calculator.calculateResult(
                        currentParagraph,
                        typingArea.getText(),
                        elapsedSeconds
                );
            } catch (ArithmeticException e) {
                System.err.println("Calculation error (likely division by zero): " + e.getMessage());
                // Create default result with setters
                finalResult = new Result();
                finalResult.setWpm(0);
                finalResult.setAccuracy(0);
                finalResult.setCorrectCharacters(0);
                finalResult.setIncorrectCharacters(0);
            } catch (Exception e) {
                System.err.println("Error calculating result: " + e.getMessage());
                e.printStackTrace();
                // Create default result with setters
                finalResult = new Result();
                finalResult.setWpm(0);
                finalResult.setAccuracy(0);
                finalResult.setCorrectCharacters(0);
                finalResult.setIncorrectCharacters(0);
            }

            // Update results panel with actual elapsed time
            ((JLabel) resultsPanel.getClientProperty("timeLabel")).setText(
                String.format("Time: %02d:%02d", minutes, seconds)
            );
            ((JLabel) resultsPanel.getClientProperty("wpmLabel")).setText("WPM: " + finalResult.getWpm());
            ((JLabel) resultsPanel.getClientProperty("accuracyLabel")).setText("Accuracy: " + finalResult.getAccuracy() + "%");
            ((JLabel) resultsPanel.getClientProperty("correctLabel")).setText("✓ Correct: " + finalResult.getCorrectCharacters());
            ((JLabel) resultsPanel.getClientProperty("errorLabel")).setText("✗ Incorrect: " + finalResult.getIncorrectCharacters());

            // Swap panels
            typingAreaPanel.setVisible(false);
            resultsPanel.setVisible(true);
            
        } catch (Exception e) {
            System.err.println("Critical error in finishTest: " + e.getMessage());
            e.printStackTrace();
            startButton.setEnabled(true);
        }
    }

    private void updateTypingStats() {
        try {
            if (currentParagraph == null || currentParagraph.isEmpty()) {
                return;
            }

            String typedText = typingArea.getText();

            if (typedText.length() >= currentParagraph.length()) {
                finishTest();
                return;
            }

            if (typingHighlighter != null) {
                try {
                    typingHighlighter.updateHighlight(typedText);
                } catch (Exception e) {
                    System.err.println("Error updating highlight: " + e.getMessage());
                }
            }

            int elapsedSeconds = (int)((System.currentTimeMillis() - startTime) / 1000);

            if (elapsedSeconds < 1) {
                elapsedSeconds = 1;
            }

            try {
                Result result = calculator.calculateResult(currentParagraph, typedText, elapsedSeconds);
                int remaining = Math.max(0, currentParagraph.length() - typedText.length());
                updateStats(result.getWpm(), result.getAccuracy(), result.getCorrectCharacters(), result.getIncorrectCharacters(), remaining);
            } catch (Exception e) {
                System.err.println("Error in stats calculation: " + e.getMessage());
                // Keep displaying current stats instead of crashing
            }
        } catch (Exception e) {
            System.err.println("Critical error in updateTypingStats: " + e.getMessage());
        }
    }

    private void updateTimerDisplay() {
        // Show ELAPSED time, not remaining time
        int elapsedSeconds = (int)((System.currentTimeMillis() - startTime) / 1000);
        int minutes = elapsedSeconds / 60;
        int seconds = elapsedSeconds % 60;
        timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
    }

    private void updateStats(int wpm, int accuracy, int correct, int errors, int remaining) {
        wpmLabel.setText("WPM: " + wpm);
        accuracyLabel.setText("Accuracy: " + accuracy + "%");
        correctLabel.setText("Correct: " + correct);
        errorLabel.setText("Incorrect: " + errors);
        remainingLabel.setText("Remaining: " + remaining);
    }

    private void configureButtonHover(JButton button) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(BUTTON_COLOR);
            }
        });
    }

    public static void main(String[] args) {
        new TypingFrame();
    }
}