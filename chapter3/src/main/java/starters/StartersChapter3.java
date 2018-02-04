package starters;

import rx.Observable;
import rx.Scheduler;

public class StartersChapter3 {
    private Scheduler scheduler;
    private SomeDataSource someDataSource;

    public StartersChapter3(Scheduler scheduler, SomeDataSource someDataSource) {
        this.scheduler = scheduler;
        this.someDataSource = someDataSource;
    }

    /*
     * Task 1: Filter, Map, FlatMap, ConcatMap: Simple
     */

    public Observable<String> toUpper(Observable<String> observable) {
        //TODO
        throw new UnsupportedOperationException();
    }

    public Observable<String> toString(Observable<?> observable) {
        //TODO
        throw new UnsupportedOperationException();
    }

    public Observable<Integer> even(Observable<Integer> observable) {
        //TODO
        throw new UnsupportedOperationException();
    }

    public <T> Observable<T> flattenEager(Observable<Observable<T>> observableOfObservables) {
        //TODO
        throw new UnsupportedOperationException();
    }

    public <T> Observable<T> flattenInOrder(Observable<Observable<T>> observableOfObservables) {
        //TODO
        throw new UnsupportedOperationException();
    }

    public Observable<SomeData> loadMany(Observable<String> ids) {
        //TODO
        throw new UnsupportedOperationException();
    }

    public Observable<SomeData> loadManyOneByOne(Observable<String> ids) {
        //TODO
        throw new UnsupportedOperationException();
    }

    public Observable<SomeData> loadManyWithLimitedConcurrency(Observable<String> ids, int concurrency) {
        //TODO
        throw new UnsupportedOperationException();
    }

    /*
     * Task 2: Filter, Map, FlatMap, ConcatMap: Combined
     */

    public Observable<Integer> allPositiveOddNumbersSmallerThan(int n) {
        //TODO
        throw new UnsupportedOperationException();
    }

    public Observable<String> evenAsString(Observable<Integer> integers) {
        //TODO
        throw new UnsupportedOperationException();
    }

    public Observable<Integer> sleepSort(Observable<Integer> observable) {
        //TODO
        throw new UnsupportedOperationException();
    }

    public Observable<Integer> compoundedSleep(Observable<Integer> observable) {
        //TODO
        throw new UnsupportedOperationException();
    }

    public Observable<String> allName(Observable<String> maleNames, Observable<String> femaleNames) {
        //TODO
        throw new UnsupportedOperationException();
    }

    public Observable<Pair<Integer, String>> pair(Observable<Integer> left, Observable<String> right) {
        //TODO
        throw new UnsupportedOperationException();
    }

    public Observable<Configuration> configuration(Observable<String> setting1, Observable<String> setting2) {
        //TODO
        throw new UnsupportedOperationException();
    }

    public Observable<String> transformWithLatestMode(Observable<String> input, Observable<Mode> modes) {
        //TODO
        throw new UnsupportedOperationException();
    }


    //Ends after Integer.MAX_VALUE
    public Observable<Pair<Integer, String>> index(Observable<String> input) {
        //TODO
        throw new UnsupportedOperationException();
    }
}
