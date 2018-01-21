package phoned.clock;

public class ClockController {
    private ClockWidget clockWidget;
    private ClockService clockService;

    public ClockController(ClockWidget clockWidget, ClockService clockService) {
        this.clockWidget = clockWidget;
        this.clockService = clockService;
    }

    public void init() {
        clockService.getTime()
                .subscribe(clockWidget::updateTime);
    }
}
