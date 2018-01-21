package phoned.notification;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import phoned.notification.ui.NotificationWidget;
import rx.Observable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NotificationControllerTest {
    private NotificationWidget notificationWidget = mock(NotificationWidget.class);
    private NotificationService notificationService = mock(NotificationService.class);
    private NotificationController notificationController = new NotificationController(notificationWidget, notificationService);

    /*
     * You look at PhoneD and you see a fancy clock. But what you do not see
     * are any notifications. It looks like PhoneD still needs some work.
     */

    /*
     * Task 6
     *
     * Your task:
     * - Implement the first TODO in notificationController.init()
     * - Do NOT yet implement the second TODO
     *
     * NotificationController is very similar to ClockController. Only this time
     * notifications are added to the widget when they arrive.
     */

    @Test
    public void whenANewNotificationArrives_thenTheWidgetIsUpdated() throws Exception {
        Notification notification1 = new Notification();
        notification1.id = "not1";
        notification1.title = "title1";
        notification1.body = "body1";

        Notification notification2 = new Notification();
        notification1.id = "not2";
        notification1.title = "title2";
        notification1.body = "body2";

        when(notificationService.getNotifications())
                .thenReturn(Observable.just(notification1, notification2));
        when(notificationWidget.getClosedNotificationIds())
                .thenReturn(Observable.never());

        notificationController.init();

        InOrder order = Mockito.inOrder(notificationWidget);
        order.verify(notificationWidget).addNotification(notification1);
        order.verify(notificationWidget).addNotification(notification2);
    }

    /*
     * You look again at PhoneD and this time you see a dummy notification.
     * You are curious and click on the notification and you see the body
     * of the notification. You click on the body and you see that it closes
     * again. But now the list of notifications is gone.
     *
     * This is not really what we wanted. Two things.
     * First, we want control over the notifications.
     * Second, we need to fix this UI bug.
     *
     * But hey! At least we are making some progress.
     *
     * We are going to fix the service first.
     */

    /*
     * Go to FileSystemNotificationServiceTest
     */

    /*
     * Continue here after NotificationDetailsWidgetTest
     */

    /*
     * Now, you are looking for a way to remove read notifications. We just added
     * an observable to NotificationDetailsWidget and you notice that the code fairy
     * implemented NotificationWidget.getClosedNotificationIds() which delegates
     * to NotificationDetailsWidget.
     *
     * You also notice that the following methods are already implemented:
     * - NotificationWidget.removeNotification()
     * - NotificationService.removeNotification()
     *
     * You decide to put this functionality in NotificationController.
     */

    /*
     * Task 9
     *
     * Your task:
     * - Complete NotificationController.init()
     */

    @Test
    public void whenTheDetailsOfANotificationAreClosed_thenTheNotificationShouldBeRemoved() throws Exception {
        when(notificationWidget.getClosedNotificationIds())
                .thenReturn(Observable.just("id"));
        when(notificationService.getNotifications())
                .thenReturn(Observable.never());

        notificationController.init();

        verify(notificationService).removeNotification("id");
        verify(notificationWidget).removeNotification("id");
    }

    /*
     * PhoneD should now be complete. You run PhoneD again and are ready to
     * test the notifications. You look in the console for the temporary
     * directory. You prepared some files in advance and copy them in the
     * temporary directory.
     *
     * After a long day of hard work, PhoneD is finally working.
     */

    /*
     * End of chapter 2
     */
}