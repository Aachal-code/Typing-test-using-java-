/**
 * Calculates typing statistics such as WPM and accuracy.
 * This class keeps calculations separate from the user interface.
 */
public class Calculator {
    public Result calculateResult(String targetText, String typedText, int elapsedSeconds) {
        int typedCharacters = typedText.length();
        int correctCharacters = 0;
        int incorrectCharacters = 0;

        for (int i = 0; i < typedCharacters; i++) {
            if (i < targetText.length() && typedText.charAt(i) == targetText.charAt(i)) {
                correctCharacters++;
            } else {
                incorrectCharacters++;
            }
        }

        double timeInMinutes = Math.max(elapsedSeconds, 1) / 60.0;
        double wpm = (typedCharacters / 5.0) / timeInMinutes;
        double accuracy = typedCharacters > 0 ? (correctCharacters / (double) typedCharacters) * 100 : 0;

        Result result = new Result();
        result.setTypedCharacters(typedCharacters);
        result.setCorrectCharacters(correctCharacters);
        result.setIncorrectCharacters(incorrectCharacters);
        result.setWpm((int) Math.round(wpm));
        result.setAccuracy((int) Math.round(accuracy));
        
        return result;
    }
}