package starters;

import rx.Observable;

public interface SomeDataSource {
    Observable<SomeData> load(String id);
}

