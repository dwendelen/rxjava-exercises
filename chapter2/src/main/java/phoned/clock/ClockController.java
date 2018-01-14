package phoned.clock;

public class ClockController {
    private ClockWidget clockWidget;
    private ClockService clockService;

    public ClockController(ClockWidget clockWidget, ClockService clockService) {
        this.clockWidget = clockWidget;
        this.clockService = clockService;
    }

    public void init() {
        //TODO The widget should somehow be updated when the time changes
    }
}
