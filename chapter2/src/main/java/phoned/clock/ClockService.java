package phoned.clock;

import rx.Observable;

import java.time.Clock;
import java.time.LocalDateTime;

public class ClockService {
    private Clock clock;
    private int tickInterval;

    public ClockService(Clock clock, int tickInterval) {
        this.clock = clock;
        this.tickInterval = tickInterval;
    }

    public void init() {
    }

    public Observable<LocalDateTime> getTime() {
        //TODO Emit the time every tickInterval milliseconds
        return Observable.just(LocalDateTime.now(clock));
    }
}
