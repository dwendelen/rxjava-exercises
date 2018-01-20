package phoned.notification;

import rx.Observable;

public interface NotificationService {
    Observable<Notification> getNotifications();
    void removeNotification(String id);
}
