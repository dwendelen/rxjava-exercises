package phoned.notification;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NotificationWidget {
    private JPanel panel;
    private JScrollPane scrollPanel;

    private JTextArea textArea;

    private GroupLayout layout;
    private GroupLayout.SequentialGroup verticalGroup;
    private GroupLayout.ParallelGroup horizontalGroup;

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
        textArea.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        textArea.setVisible(false);
                        scrollPanel.setVisible(true);
                    }
                }
        );
        textArea.setEditable(false);
        textArea.setVisible(false);
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
                        textArea.setText(notification.body);
                        scrollPanel.setVisible(false);
                        textArea.setVisible(true);
                    }
                }
        );

        verticalGroup.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
        horizontalGroup.addComponent(textField);
    }
}
