package phoned;

import phoned.clock.ClockWidget;

import javax.swing.*;

public class Window {
    private ClockWidget clockWidget;

    private JFrame frame = new JFrame();

    private GroupLayout layout = new GroupLayout(frame.getContentPane());
    private GroupLayout.SequentialGroup verticalGroup = layout.createSequentialGroup();
    private GroupLayout.ParallelGroup horizontalGroup = layout.createParallelGroup();

    public Window(ClockWidget clockWidget) {
        this.clockWidget = clockWidget;
    }

    public void init() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setTitle("Phone D");
        frame.setSize(200, 400);
        frame.setResizable(false);
        frame.getContentPane().setLayout(layout);

        layout.setVerticalGroup(verticalGroup);
        layout.setHorizontalGroup(horizontalGroup);
        layout.setAutoCreateContainerGaps(true);

        clockWidget.init();
        clockWidget.addTo(verticalGroup, horizontalGroup);
    }

    public void show() {
        frame.setVisible(true);
    }
}
