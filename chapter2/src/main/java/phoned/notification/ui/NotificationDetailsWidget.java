package phoned.notification.ui;

import phoned.notification.Notification;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NotificationDetailsWidget {
    private JTextArea textArea;

    public NotificationDetailsWidget(JTextArea textArea) {
        this.textArea = textArea;
    }

    private Subject<String, String> closedNotificationIds = PublishSubject.create();

    public void showDetails(Notification notification) {
        textArea.setText(notification.body);
        textArea.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        textArea.setVisible(false);
                        textArea.removeMouseListener(this);
                        closedNotificationIds.onNext(notification.id);
                    }
                }
        );
        textArea.setVisible(true);
    }

    public Observable<String> getClosedNotificationIds() {
        return closedNotificationIds;
    }
}
