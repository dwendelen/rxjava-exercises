package phoned.clock;

import rx.Observable;

import java.time.Clock;
import java.time.LocalDateTime;

public class ClockService {
    private Clock clock;
    private int tickInterval;

    private Observable<LocalDateTime> time;

    public ClockService(Clock clock, int tickInterval) {
        this.clock = clock;
        this.tickInterval = tickInterval;
    }

    public void init() {
        time = Observable.<LocalDateTime>create(sub -> {
            Runnable r = () -> {
                try {
                    while (!sub.isUnsubscribed()) {
                        sub.onNext(LocalDateTime.now(clock));
                        Thread.sleep(tickInterval);
                    }
                } catch (Exception e) {
                    sub.onError(e);
                }
            };
            new Thread(r).start();
        }).share();
    }

    public Observable<LocalDateTime> getTime() {
        return time;
    }
}
