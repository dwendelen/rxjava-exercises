package phoned.clock;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import rx.Observable;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClockControllerTest {
    private ClockWidget clockWidget = mock(ClockWidget.class);
    private ClockService clockService = mock(ClockService.class);
    private ClockController clockController = new ClockController(clockWidget, clockService);

    /*
     * One day you have a new crazy idea. You fill in the crazy-idea-form and
     * come to the conclusion that it is a good idea.
     *
     * Nowadays software is replacing hardware at a fast pace. But smartphones
     * have not been made in software yet. You decided that you are going to
     * build the first smartphone in software named PhoneD.
     *
     * You just started reading a book about RxJava and decide that it is an
     * excellent technology for PhoneD. You are a genius!
     */

    /*
     * PhoneD is a simple swing application.
     *
     * The app is split per functionality. Every piece of functionality is
     * sliced in three layers:
     * - UI (Widgets): Contains and displays Swing components
     * - Service layer: Contains the 'business' logic
     * - Controller: Coordinates the UI and the service layer
     */

    /*
     * Task 1
     *
     * Your task:
     * - Run PhoneDChapter2
     *
     * PhoneD has two pieces of functionality:
     * - A clock that displays the current time
     * - Notifications with a title and content
     *
     * Run PhoneD and take a first look.
     */

    /*
     * You took a first look and you saw that PhoneD looks a bit dull.
     * The clock stands still at 00:00:00 and there are no notifications.
     *
     * We will start working on the clock.
     */

    /*
     * Task 2
     *
     * Your task:
     * - Implement clockController.init()
     *
     * The ClockService provides the current time. Every time the time changes
     * a new event is pushed on the observable. But it is not yet displayed in
     * the UI. Your task is to fix this.
     *
     * Hint:
     * - Observable.subscribe
     */

    @Test
    public void whenTheTimeChanges_thenTheWidgetIsUpdated() throws Exception {
        LocalDateTime time1 = LocalDateTime.of(2018, 1, 14, 15, 2, 1);
        LocalDateTime time2 = LocalDateTime.of(2018, 1, 14, 15, 2, 2);

        when(clockService.getTime())
                .thenReturn(Observable.just(time1, time2));

        clockController.init();

        InOrder order = Mockito.inOrder(clockWidget);
        order.verify(clockWidget).updateTime(time1);
        order.verify(clockWidget).updateTime(time2);
    }

    /*
     * You fixed the controller and took a new look at PhoneD. Now it shows the
     * current time, but it is not updating.
     *
     * Maybe it is a good idea to have a look in the ClockService.
     */

    /*
     * Go to ClockServiceTest
     */
}