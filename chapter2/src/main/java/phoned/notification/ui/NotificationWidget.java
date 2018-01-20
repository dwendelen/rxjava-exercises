package phoned.notification.ui;

import phoned.notification.Notification;
import rx.Observable;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class NotificationWidget {
    private JPanel panel;
    private JScrollPane scrollPanel;

    private JTextArea textArea;
    private NotificationDetailsWidget notificationDetailsWidget;

    private GroupLayout layout;
    private GroupLayout.SequentialGroup verticalGroup;
    private GroupLayout.ParallelGroup horizontalGroup;

    private Map<String, JTextField> notifications = new HashMap<>();

    public void init() {
        panel = new JPanel();

        layout = new GroupLayout(panel);
        panel.setLayout(layout);

        verticalGroup = layout.createSequentialGroup();
        horizontalGroup = layout.createParallelGroup();

        layout.setVerticalGroup(verticalGroup);
        layout.setHorizontalGroup(horizontalGroup);
        layout.setAutoCreateContainerGaps(true);

        scrollPanel = new JScrollPane(panel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setVisible(false);

        notificationDetailsWidget = new NotificationDetailsWidget(textArea);
        notificationDetailsWidget.getClosedNotificationIds()
                .subscribe(a -> scrollPanel.setVisible(true));
    }

    public void addTo(GroupLayout.SequentialGroup verticalGroup, GroupLayout.ParallelGroup horizontalGroup) {
        verticalGroup.addComponent(scrollPanel);
        horizontalGroup.addComponent(scrollPanel);

        verticalGroup.addComponent(textArea);
        horizontalGroup.addComponent(textArea);
    }

    public void addNotification(Notification notification) {
        JTextField textField = new JTextField(notification.title);
        textField.setEditable(false);
        textField.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        scrollPanel.setVisible(false);
                        notificationDetailsWidget.showDetails(notification);
                    }
                }
        );

        notifications.put(notification.id, textField);

        verticalGroup.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
        horizontalGroup.addComponent(textField);
        panel.revalidate();
    }

    public void removeNotification(String notificationId) {
        JTextField textField = notifications.get(notificationId);

        if(textField == null) {
            return;
        }

        notifications.remove(notificationId);
        panel.remove(textField);
    }

    public Observable<String> getClosedNotificationIds() {
        return notificationDetailsWidget.getClosedNotificationIds();
    }
}
