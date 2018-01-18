package phoned.clock;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClockWidget {
    private JLabel label = new JLabel();

    public void init() {
        label.setText("00:00:00");
        label.setFont(label.getFont().deriveFont(32f));
    }

    public void addTo(GroupLayout.SequentialGroup verticalGroup, GroupLayout.ParallelGroup horizontalGroup) {
        verticalGroup.addComponent(label);
        horizontalGroup.addComponent(label, GroupLayout.Alignment.CENTER);
    }

    public void updateTime(LocalDateTime time) {
        label.setText(time.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }
}
