package phoned.notification;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import rx.Observable;

import static org.mockito.Mockito.mock;
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
     * - Implement notificationController.init()
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

        notificationController.init();

        InOrder order = Mockito.inOrder(notificationWidget);
        order.verify(notificationWidget).addNotification(notification1);
        order.verify(notificationWidget).addNotification(notification2);
    }

    /*
     * You look again at PhoneD and this time you see a dummy notification.
     * You are curious and click on the notification and you see the body
     * of the notification. You click on the body and you see that it closes
     * again.
     *
     * This is not really what we wanted. We want control over the notifications.
     * But hey! At least we are making some progress.
     */

    /*
     * Go to FileSystemNotificationServiceTest
     */
}