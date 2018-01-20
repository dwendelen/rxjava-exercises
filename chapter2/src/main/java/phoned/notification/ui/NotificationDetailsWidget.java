package phoned.notification.ui;

import phoned.notification.Notification;
import rx.Observable;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NotificationDetailsWidget {
    private JTextArea textArea;

    public NotificationDetailsWidget(JTextArea textArea) {
        this.textArea = textArea;
    }

    public void showDetails(Notification notification) {
        textArea.setText(notification.body);
        textArea.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        textArea.setVisible(false);
                        textArea.removeMouseListener(this);
                        //TODO Omit an event
                    }
                }
        );
        textArea.setVisible(true);
    }

    public Observable<String> getClosedNotificationIds() {
        return Observable.never();
    }
}
