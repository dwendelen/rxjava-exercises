package phoned.notification;

import phoned.notification.ui.NotificationWidget;

public class NotificationController {
    private NotificationWidget notificationWidget;
    private NotificationService notificationService;

    public NotificationController(NotificationWidget notificationWidget, NotificationService notificationService) {
        this.notificationWidget = notificationWidget;
        this.notificationService = notificationService;
    }

    public void init() {
        //TODO The widget should somehow be updated when a notification arrives.
        //          Do not yet start with the next TODO

        //TODO Notifications should somehow be deleted when the widget closed
        //          the notifications details
    }
}
