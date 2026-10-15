import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.Color;

/**
 * Handles live visual feedback while the user types.
 * TypingHighlighter compares typed text to the target paragraph and updates
 * character styles in the JTextPane document.
 */
public class TypingHighlighter {
    private static final Color DEFAULT_COLOR = new Color(100, 102, 105);
    private static final Color CORRECT_COLOR = new Color(226, 183, 20);
    private static final Color INCORRECT_COLOR = new Color(202, 71, 84);
    private static final Color NEXT_CHAR_BACKGROUND = new Color(61, 62, 66);

    private final StyledDocument document;
    private final String targetText;

    public TypingHighlighter(StyledDocument document, String targetText) {
        this.document = document;
        this.targetText = targetText;
    }

    /**
     * Inserts the target paragraph into the document using default gray styling.
     */
    public void applyDefaultStyle() {
        SimpleAttributeSet defaultStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(defaultStyle, DEFAULT_COLOR);
        StyleConstants.setFontFamily(defaultStyle, "Monospaced");

        try {
            document.remove(0, document.getLength());
            document.insertString(0, targetText, defaultStyle);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the paragraph text colors based on the typed input.
     * Correct characters become yellow, incorrect become red, and the next
     * expected character is highlighted.
     */
    public void updateHighlight(String typedText) {
        int typedLength = Math.min(typedText.length(), targetText.length());

        for (int i = 0; i < targetText.length(); i++) {
            SimpleAttributeSet style = new SimpleAttributeSet();
            StyleConstants.setFontFamily(style, "Monospaced");
            StyleConstants.setBackground(style, new Color(0, 0, 0, 0));

            if (i < typedLength) {
                if (typedText.charAt(i) == targetText.charAt(i)) {
                    StyleConstants.setForeground(style, CORRECT_COLOR);
                } else {
                    StyleConstants.setForeground(style, INCORRECT_COLOR);
                }
            } else {
                StyleConstants.setForeground(style, DEFAULT_COLOR);
            }

            if (i == typedLength && typedLength < targetText.length()) {
                StyleConstants.setBackground(style, NEXT_CHAR_BACKGROUND);
            }

            document.setCharacterAttributes(i, 1, style, false);
        }
    }
}
