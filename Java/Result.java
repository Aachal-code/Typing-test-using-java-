/**
 * Encapsulates calculation results for a typing test.
 * Result stores WPM, accuracy, and character counts.
 */
public class Result {
    private int typedCharacters;
    private int correctCharacters;
    private int incorrectCharacters;
    private int wpm;
    private int accuracy;

    public int getTypedCharacters() {
        return typedCharacters;
    }

    public void setTypedCharacters(int typedCharacters) {
        this.typedCharacters = typedCharacters;
    }

    public int getCorrectCharacters() {
        return correctCharacters;
    }

    public void setCorrectCharacters(int correctCharacters) {
        this.correctCharacters = correctCharacters;
    }

    public int getIncorrectCharacters() {
        return incorrectCharacters;
    }

    public void setIncorrectCharacters(int incorrectCharacters) {
        this.incorrectCharacters = incorrectCharacters;
    }

    public int getWpm() {
        return wpm;
    }

    public void setWpm(int wpm) {
        this.wpm = wpm;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }
}
