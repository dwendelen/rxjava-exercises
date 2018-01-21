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
        notificationService.getNotifications()
                .subscribe(notificationWidget::addNotification);

        notificationWidget.getClosedNotificationIds()
                .subscribe(id -> {
                    notificationService.removeNotification(id);
                    notificationWidget.removeNotification(id);
                });
    }
}
