package catchorwaste.controller;

import catchorwaste.model.TimerModel;
import catchorwaste.view.TimerView;
import javafx.animation.AnimationTimer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TimerControllerTest {

    private TimerController timerController;
    private TimerModel timerModelMock;
    private TimerView timerViewMock;
    private AnimationTimer animationTimerMock;

    @BeforeEach
    void setUp() {
        timerModelMock = mock(TimerModel.class);
        timerViewMock = mock(TimerView.class);
        animationTimerMock = mock(AnimationTimer.class);

      //  timerController = new TimerController(timerModelMock, timerViewMock, timerController);
    }

    @Test
    void testStartTimer() {
        timerController.startTimer();
        verify(animationTimerMock).start();
    }

    @Test
    void testUpdateTimer() {
        // Define test data
        long now = 1000000L; // Example value
        long startTimeNano = 0L; // Example value

        // Mock method calls
        when(timerModelMock.getMinutes()).thenReturn(2); // Example value
        when(timerModelMock.getSeconds()).thenReturn(30); // Example value
        when(timerModelMock.getTotalSeconds()).thenReturn(150); // Example value
        when(System.nanoTime()).thenReturn(now);
        timerController.startTimer(); // Start the timer

        // Call method under test
       // timerController.updateTimer(now);

        // Verify expected behavior
        verify(timerModelMock).setTotalSeconds(147); // Verify that the remaining time is correctly updated
        verify(timerViewMock).updateTimer(2, 27); // Verify that the timer view is correctly updated
    }

    @Test
    void testStopTimer() {
        timerController.stopTimer();
        verify(animationTimerMock).stop();
    }


}
