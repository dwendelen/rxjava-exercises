package phoned.notification.ui;

import org.junit.Test;
import phoned.notification.Notification;
import rx.observers.TestSubscriber;

import javax.swing.*;
import java.awt.event.MouseListener;

public class NotificationDetailsWidgetTest {
    private static final String NOTIFICATION_ID = "notification-id";

    private JTextArea textField = new JTextArea();
    private NotificationDetailsWidget notificationDetailsWidget =
            new NotificationDetailsWidget(textField);

    private TestSubscriber<String> testSubscriber = new TestSubscriber<>();

    /*
     * Ok, now it's time to fix that UI bug.
     *
     * After some investigation you discover that the list of notifications is
     * shown when the NotificationDetailsWidget pushes an event. But
     * NotificationDetailsWidget is not pushing any events.
     */

    /*
     * Task 8
     *
     * Your task:
     * - Emit events when the details panel should be closed. Use one of
     *      the Subjects.
     *
     * To fix this bug, the NotificationDetailsWidget should send out events
     * when the users click on the textArea. But you are tired of manually
     * creating observables. You decide to be lazy and use one of Subjects
     * provided by RxJava.
     */

    @Test
    public void whenWeClickOnTheDetails_thenWeClosedTheDetails() {
        Notification notification = new Notification();
        notification.id = NOTIFICATION_ID;

        notificationDetailsWidget.showDetails(notification);

        notificationDetailsWidget.getClosedNotificationIds()
                .subscribe(testSubscriber);

        MouseListener[] mouseListeners = textField.getMouseListeners();
        MouseListener ourListener = mouseListeners[mouseListeners.length - 1];
        ourListener.mouseClicked(null);

        testSubscriber.assertValues(NOTIFICATION_ID);
    }

    /*
     * You fixed the UI bug and now everything works as expected.
     *
     * Unfortunately, there is still something bugging you. When you have read
     * the details of a notification then it makes no longer sense to keep the
     * notification on the phone.
     *
     * Let's fix this annoyance.
     */

    /*
     * Go to NotificationControllerTest, continue at
     *      "Continue here after NotificationDetailsWidgetTest"
     */
}