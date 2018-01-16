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
}