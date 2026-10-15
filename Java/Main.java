import javax.swing.SwingUtilities;

/**
 * Entry point for the Typing Speed Tester application.
 * This class only starts the Swing application on the Event Dispatch Thread.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TypingFrame());
    }
}
