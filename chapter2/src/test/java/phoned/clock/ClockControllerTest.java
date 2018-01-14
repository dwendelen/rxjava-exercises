package phoned.clock;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import rx.Observable;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClockControllerTest {
    private ClockWidget clockWidget = mock(ClockWidget.class);
    private ClockService clockService = mock(ClockService.class);
    private ClockController clockController = new ClockController(clockWidget, clockService);

    @Test
    public void whenTheTimeChanges_thenTheWidgetIsUpdated() throws Exception {
        LocalDateTime time1 = LocalDateTime.of(2018, 1, 14, 15, 2, 1);
        LocalDateTime time2 = LocalDateTime.of(2018, 1, 14, 15, 2, 2);

        when(clockService.getTime())
                .thenReturn(Observable.just(time1, time2));

        clockController.init();

        InOrder order = Mockito.inOrder(clockWidget);
        order.verify(clockWidget).updateTime(time1);
        order.verify(clockWidget).updateTime(time2);
    }
}