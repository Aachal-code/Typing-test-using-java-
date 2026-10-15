import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Manages countdown timing for the typing test.
 * TimerManager handles scheduling and notifying listeners when the timer updates or finishes.
 */
public class TimerManager {
    private final Timer timer;
    private int secondsRemaining;
    private TimerUpdateListener listener;

    public TimerManager() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (secondsRemaining > 0) {
                    secondsRemaining--;
                    notifyTimeChanged();
                    if (secondsRemaining == 0) {
                        stop();
                        notifyTimerFinished();
                    }
                }
            }
        });
    }

    public void start(int startingSeconds) {
        secondsRemaining = startingSeconds;
        notifyTimeChanged();
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public int getSecondsRemaining() {
        return secondsRemaining;
    }

    public void setListener(TimerUpdateListener listener) {
        this.listener = listener;
    }

    private void notifyTimeChanged() {
        if (listener != null) {
            listener.onTimeChanged(secondsRemaining);
        }
    }

    private void notifyTimerFinished() {
        if (listener != null) {
            listener.onTimerFinished();
        }
    }

    public interface TimerUpdateListener {
        void onTimeChanged(int secondsRemaining);
        void onTimerFinished();
    }
}

