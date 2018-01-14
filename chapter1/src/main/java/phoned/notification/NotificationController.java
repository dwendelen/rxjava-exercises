package phoned.notification;

public class NotificationController {
    private NotificationWidget notificationWidget;
    private NotificationService notificationService;

    public NotificationController(NotificationWidget notificationWidget, NotificationService notificationService) {
        this.notificationWidget = notificationWidget;
        this.notificationService = notificationService;
    }

    public void init() {
        //TODO The widget should somehow be updated when a notification arrives
    }
}
