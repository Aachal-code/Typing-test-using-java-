import java.util.List;
import java.util.Random;

/**
 * Provides random paragraphs for typing practice.
 *
 * This class stores a curated set of sample paragraphs and returns one at
 * random each time the test begins.
 */
    public class TextGenerator {
        private static final List<String> PARAGRAPHS = List.of(
                "Typing speed has become an essential skill in today's digital world. Whether you're working in an office or studying online, the ability to type quickly and accurately can significantly improve your productivity and efficiency.",
        
            "The QWERTY keyboard layout was designed in the 1870s to prevent mechanical jams on old typewriters. Interestingly, this layout has remained unchanged and continues to be the standard on modern computers and devices.",
            
            "Proper typing technique starts with correct finger placement on the home row of the keyboard. This foundation allows your fingers to reach all keys efficiently and helps you build muscle memory for faster typing.",
            
            "Touch typing, which means typing without looking at the keyboard, is a skill that takes time to develop but greatly improves your speed. Most professional typists rely on this technique to maintain high accuracy and performance.",
            
            "Regular practice is essential for improving your typing skills and building lasting muscle memory. Setting daily goals and tracking your progress helps maintain motivation as you work to improve.",
            
            "Good ergonomics and proper posture are crucial for both typing performance and preventing injury. Investing in quality equipment and taking regular breaks can help you type comfortably for longer periods.",
            
            "Online typing tests provide immediate feedback on your speed and accuracy while helping you identify areas for improvement. Many platforms also include engaging games that make practice more enjoyable and motivating.",
            
            "Accuracy should always be prioritized over raw speed when learning to type. Building good habits early on prevents mistakes from becoming ingrained and sets the foundation for long-term success.",
            
            "The average adult types between forty and sixty words per minute, while professional typists often exceed eighty words per minute. Achieving these speeds requires dedication, consistent practice, and proper technique.",
            
            "Typing competitions around the world showcase the incredible skills of elite typists and push the boundaries of human performance. These events inspire others to improve their skills and demonstrate what is possible with dedication."
        );
        
    private final Random random;

    public TextGenerator() {
        random = new Random();
    }

    /**
     * Returns a random paragraph from the stored list.
     *
     * @return a paragraph string for the typing test
     */
    public String getRandomParagraph() {
        int index = random.nextInt(PARAGRAPHS.size());
        return PARAGRAPHS.get(index);
    }
}
