Typing Speed Tester
====================

Overview
--------
Typing Speed Tester is a Java Swing desktop application designed to help users practice typing speed and accuracy in a focused environment. It presents a randomly selected paragraph, offers immediate visual feedback for typed characters, and computes typing statistics such as words per minute (WPM), accuracy, and character counts.

Purpose
-------
- Provide a simple typing practice tool with a clean interface.
- Offer immediate performance feedback while typing.
- Measure typing speed and accuracy in a 60-second timed session.
- Support repeated practice sessions with new text each time.

Architecture
------------
The application is organized into separate responsibilities to keep UI, timing, text generation, highlighting, and calculation logic distinct.

Component Responsibilities
--------------------------
- `Main.java`
  - Application entry point.
  - Starts the Swing UI on the Event Dispatch Thread using `SwingUtilities.invokeLater()`.

- `TypingFrame.java`
  - Constructs the main window and user interface layout.
  - Handles button actions, document updates, and panel swapping.
  - Manages the typing session lifecycle: start, restart, live updates, and finish.
  - Displays live statistics and final results.

- `TextGenerator.java`
  - Contains a fixed set of training paragraphs.
  - Returns a random paragraph each time a new test begins.

- `TypingHighlighter.java`
  - Applies color-coded styling to the target paragraph shown in the UI.
  - Updates the text display as the user types to highlight correct, incorrect, and next expected characters.
  - Uses `StyledDocument` to apply per-character attributes.

- `TimerManager.java`
  - Manages a Swing `Timer` with a 1-second tick.
  - Notifies listeners whenever the countdown changes or reaches zero.
  - Allows the UI to update the timer display and finish the test.

- `Calculator.java`
  - Calculates typing statistics from the typed text and elapsed time.
  - Computes WPM as `(typedCharacters / 5) / minutes`.
  - Computes accuracy as `correctCharacters / typedCharacters * 100`.

- `Result.java`
  - Stores the final typing result values.
  - Provides getters and setters for typed character counts, WPM, and accuracy.

UI Flow
-------
1. The user clicks `Start`.
2. A new paragraph is generated and displayed in the target text pane.
3. The typing input area is enabled, and the 60-second timer begins.
4. As the user types, `TypingHighlighter` colors correct characters in yellow and incorrect characters in red.
5. Statistics for WPM, accuracy, correct characters, incorrect characters, and remaining characters update in real time.
6. When the timer expires or the paragraph is completed, the test finishes and the final results panel appears.
7. The user may click `Restart` to run a new session immediately.

User Interface Details
----------------------
- Title area with application name and subtitle.
- Timer and performance statistics displayed at the top.
- Target paragraph shown in a read-only text pane.
- Typing area below the paragraph, initially disabled until the session starts.
- Start and Restart buttons with hover styling.
- Results summary panel displayed after the session ends.

Implementation Notes
--------------------
- The timer is driven by `TimerManager`, which counts down from 60 seconds and calls back into `TypingFrame`.
- The typed input is evaluated continuously using a `DocumentListener`.
- When the user reaches the end of the paragraph, the application finishes the test early and shows results.
- `TypingHighlighter` wraps paragraph styling logic and recovers from text layout errors by logging `BadLocationException` stack traces.

Exception Handling
------------------
- `TypingHighlighter.applyDefaultStyle()` catches `BadLocationException` during text insertion.
- Any styling exception is printed to the console using `e.printStackTrace()`.
- This keeps the UI responsive even if the document model update fails.

Compile and Run
---------------
Minimum requirements:
- Java Development Kit (JDK) 8 or newer.

Steps:
1. Open a terminal in the project directory.
2. Compile all Java source files:
   ```
   javac *.java
   ```
3. Run the application:
   ```
   java Main
   ```

Usage
-----
1. Click `Start` to begin a typing session.
2. Type the paragraph shown in the read-only target text pane.
3. Watch the live timer and real-time stats.
4. Incorrect characters appear in red; correct characters appear in yellow.
5. The current character position is highlighted for easier tracking.
6. When the session ends, review the results panel.
7. Click `Restart` to start a new paragraph and try again.

Tips for Users
--------------
- Focus on accuracy first; speed improves as correct typing becomes consistent.
- Use the highlighted next character to follow the paragraph more easily.
- Restart sessions frequently to practice with different paragraphs.

Known Limitations
-----------------
- The application currently supports only a single paragraph session at a time.
- The text generation uses a fixed in-memory paragraph list instead of external data.
- There is no persistence of scores across runs.
- The UI does not currently support keyboard shortcuts beyond normal focus behavior.

Potential Enhancements
----------------------
- Add multiple difficulty levels or paragraph lengths.
- Save user progress or high score history.
- Add custom paragraph input or external text file loading.
- Introduce keyboard shortcuts for start/restart and navigation.
