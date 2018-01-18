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

    /*
     * Welcome to the ClockService.
     *
     * ClockService.getTime() returns an observable that ticks once every
     * TICK_INTERVAL. Every tick will produce the current time.
     */

    /*
     * Task 3
     *
     * Your task:
     * - Create a naive observable with Observable.create() that returns the
     *      current time every TICK_INTERVAL in loop.
     *
     * You think lets start simple. I want just an infinite loop that emits the
     * current time. That can't be that hard.
     */

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

    /*
     * Task 3
     *
     * Your task:
     * - Handle exceptions properly
     *
     * The loop looks good, but we have not thought about error handling yet.
     */
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

    /*
     * Task 4
     *
     * Your task:
     * - Stop emitting events when the subscriber unsubscribes.
     *
     * It starts to look good! But you suddenly remember that you are building
     * a smartphone. And battery life matters. You remember that consulting the
     * clock requires a lot of energy. We should not emit the current time when
     * no one is interested.
     */
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

    /*
     * Task 5
     *
     * Your task:
     * - There should be only one thread running, even with multiple subscribers
     *
     * Now that you have fixed the energy usage you suddenly remember that the
     * clock was built by a first year student. You also remember that it behaves
     * funky when multiple thread try to access it.
     */
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

    /*
     * The ClockServices seems to work as expected. You run PhoneD again and
     * you see that the clock is updated. Splendid!
     *
     * Next, we will fix the notifications.
     */

    /*
     * Go to NotificationControllerTest
     */

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