package phoned.clock;

import org.junit.Before;
import org.junit.Test;
import rx.observers.TestSubscriber;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.mockito.Mockito.*;

public class ClockServiceTest {
    /**
     * Increase this value to make the tests more stable
     * Decrease this value to make the tests faster
     */
    private static final int TICK_INTERVAL = 50;
    private static final int HALF_A_TICK = TICK_INTERVAL / 2;

    private Clock clock = mockClock();
    private ClockService clockService = new ClockService(clock, TICK_INTERVAL);

    private TestSubscriber<LocalDateTime> testSubscriber = TestSubscriber.create();

    @Before
    public void setUp() throws Exception {
        clockService.init();
    }

    @Test
    public void theClockServiceTicksAtTheCorrectPace() throws Exception {
        clockService.getTime()
                .subscribe(testSubscriber);

        Thread.sleep(HALF_A_TICK);
        testSubscriber.assertValueCount(1);
        Thread.sleep(TICK_INTERVAL);
        testSubscriber.assertValueCount(2);
        Thread.sleep(TICK_INTERVAL);
        testSubscriber.assertValueCount(3);
    }

    @Test
    public void theClockServiceReturnsTheCorrectTime() throws Exception {
        clockService.getTime()
                .take(3)
                .subscribe(testSubscriber);

        Thread.sleep(4 * TICK_INTERVAL);

        testSubscriber.assertValues(
                dateTime(1).toLocalDateTime(),
                dateTime(2).toLocalDateTime(),
                dateTime(3).toLocalDateTime()
        );
    }

    @Test
    public void theClockServiceHandlesExceptionsGracefully() throws Exception {
        when(clock.instant())
                .thenReturn(
                        null,
                        dateTime(1).toInstant()
                );

        clockService.getTime()
                .subscribe(testSubscriber);

        Thread.sleep(HALF_A_TICK);
        Thread.sleep(TICK_INTERVAL);

        testSubscriber.assertNoValues();
        testSubscriber.assertError(NullPointerException.class);
    }

    @Test
    public void theObservableStopsRunningWhenTheSubscriberUnsubscribes() throws Exception {
        clockService.getTime()
                .subscribe(testSubscriber);

        Thread.sleep(HALF_A_TICK);
        testSubscriber.assertValueCount(1);
        testSubscriber.unsubscribe();

        Thread.sleep(TICK_INTERVAL);

        verify(clock, times(1)).instant();
    }

    @Test
    public void theClockIsOnlyPolledFromOneSource() throws Exception {
        clockService.getTime()
                .subscribe(testSubscriber);
        clockService.getTime()
                .subscribe(testSubscriber);

        Thread.sleep(HALF_A_TICK);
        testSubscriber.assertValueCount(2);
        testSubscriber.unsubscribe();

        verify(clock, times(1)).instant();
    }

    private Clock mockClock() {
        Clock clock = mock(Clock.class);

        when(clock.getZone())
                .thenReturn(ZoneId.systemDefault());

        when(clock.instant())
                .thenReturn(
                        dateTime(1).toInstant(),
                        dateTime(2).toInstant(),
                        dateTime(3).toInstant()
                );
        return clock;
    }

    private ZonedDateTime dateTime(int seconds) {
        return ZonedDateTime.of(2018, 1, 14, 19, 51, seconds, 0, ZoneId.systemDefault());
    }
}