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

    /**
     * When observable emits a string, then the returned observable will
     * emit that string in upper case.
     */
    public Observable<String> toUpper(Observable<String> observable) {
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * When observable emits an object, then the returned observable will
     * emit the string representation of that object.
     */
    public Observable<String> toString(Observable<?> observable) {
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * When observable emits an integer, then the returned observable will
     * either emit that some integer or drop it.
     *
     * If the integer is even, then it will be emitted, otherwise it will be
     * dropped.
     */
    public Observable<Integer> even(Observable<Integer> observable) {
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * This method eagerly flattens the observable.
     *
     * We say that the outer observable is an observable of the inner observables.
     *
     * When the outer observable emits an inner observable, then the returned
     * observable is immediately subscribing to it. When one of the inner
     * observables emits an event, then this event is emitted by the returned
     * observable.
     */
    public <T> Observable<T> flattenEager(Observable<Observable<T>> observableOfObservables) {
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * This method flattens the observable in a fixed order.
     *
     * We say that the outer observable is an observable of the inner observables.
     *
     * When the outer observable emits the first inner observable, then the returned
     * observable is immediately subscribing to it. Every time the first inner
     * observable emits an event, the returned observable emits that event.
     *
     * When the outer observable emits a second inner observable, then the returned
     * observable will only subscribe to it if the first has already completed.
     * Otherwise it will wait until the first observable has completed.
     *
     * When the first observable is completed, then the returned observable will
     * subscribe to the second observable and emit all the events emitted by the
     * second observable.
     *
     * The third inner observable must wait until the second inner observable completes
     * and so on.
     */
    public <T> Observable<T> flattenInOrder(Observable<Observable<T>> observableOfObservables) {
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * Every time ids emits an id, then the returned observable will call
     * someDataSource.load(id). SomeDataSource.load() will return observable
     * which we will call the data observable.
     *
     * When the data observable emits the data, then the returned observable
     * will emit that data.
     *
     * When multiple ids arrives before the data object has been emitted, then
     * the those requests will be handled in parallel.
     */
    public Observable<SomeData> loadMany(Observable<String> ids) {
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * Every time ids emits an id, then the returned observable will call
     * someDataSource.load(id). SomeDataSource.load() will return observable
     * which we will call the data observable.
     *
     * When the data observable emits the data, then the returned observable
     * will emit that data.
     *
     * When multiple ids arrives before the data object has been emitted, then
     * the those ids will be handled sequentially. The next id will be
     * handled after the previous observable has completed.
     */
    public Observable<SomeData> loadManyOneByOne(Observable<String> ids) {
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * Every time ids emits an id, then the returned observable will call
     * someDataSource.load(id). SomeDataSource.load() will return observable
     * which we will call the data observable.
     *
     * When the data observable emits the data, then the returned observable
     * will emit that data.
     *
     * When multiple ids arrives before the data object has been emitted, then
     * the those requests will be handled in parallel as long as the number
     * of concurrent requests is equal to or lower than concurrency.
     *
     * If there are more pending requests than allowed, then the first n
     * requests will happen in parallel. The (n + 1)th request must wait until
     * the first request has completed an so forth.
     */
    public Observable<SomeData> loadManyWithLimitedConcurrency(Observable<String> ids, int concurrency) {
        //TODO
        throw new UnsupportedOperationException();
    }

    /*
     * Task 2: Filter, Map, FlatMap, ConcatMap and Factory Methods Combined
     */

    /**
     * The returned observable will emit all odd positive numbers that are
     * strictly smaller than n.
     */
    public Observable<Integer> allPositiveOddNumbersSmallerThan(int n) {
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * When observable emits an integer, then the returned observable will
     * either emit a string or drop it.
     *
     * If the integer is even, then the returned observable will emit a string
     * representation of that integer, otherwise it will drop the integer.
     */
    public Observable<String> evenAsString(Observable<Integer> integers) {
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * This method performs sleep sort on integers. Sleep sort is a sorting
     * algorithm that sorts integer by sleeping for an amount of time that
     * is proportional to that integer before emitting that value.
     *
     * For example: iterable = [4, 5, 1, 9]
     *     at t = 1 ms -> emit 1
     *     at t = 4 ms -> emit 4
     *     at t = 5 ms -> emit 5
     *     at t = 9 ms -> emit 9
     *
     * This method uses the injected scheduler for scheduling.
     */
    public Observable<Integer> sleepSort(Iterable<Integer> integers) {
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * The returned observable emits the items in the iterable in the same
     * order as the iterable. The n-th item with value i is emitted
     * i milliseconds after the (n - 1)th item. The first item with value i0
     * is emitted after i0 milliseconds after the subscription to the returned
     * observable.
     *
     * For example: iterable = [4, 5, 1, 9]
     *     at t =  4 ms -> emit 4
     *     at t =  9 ms -> emit 5
     *     at t = 10 ms -> emit 1
     *     at t = 19 ms -> emit 9
     *
     * This method uses the injected scheduler for scheduling.
     */
    public Observable<Integer> compoundedSleep(Iterable<Integer> integers) {
        //TODO
        throw new UnsupportedOperationException();
    }

    /*
     * Task 3: Combining Observables
     */

    /**
     * The returned observable combines the male and female names in one stream.
     * Every time one of the two inputs emits a name, that name will be emitted
     * by the returned observable.
     */
    public Observable<String> allName(Observable<String> maleNames, Observable<String> femaleNames) {
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * The returned observable combines the stream of integers and strings by
     * pairing the emitted items.
     *
     * For example the following streams:
     *      [ 4,     5,     6,     7   ] and
     *      [   a,     z,     p,     r ]
     * are combined in the following pairs
     *      [(4,a), (5,z), (6,p), (7,r)]
     */
    public Observable<Pair<Integer, String>> pair(Observable<Integer> left, Observable<String> right) {
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * The stream of configurations emits a new configuration every time one of
     * the settings changes.
     *
     * @param setting1
     *      Should emit a string every time setting1 changes
     * @param setting2
     *      Should emit a string every time setting2 changes
     */
    public Observable<Configuration> configuration(Observable<String> setting1, Observable<String> setting2) {
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * The returned observable transforms the input based on the last mode.
     * If there is no mode yet known, then the inputs are dropped.
     *
     * @param mode
     *      Should emit a mode every time the mode changes
     */
    public Observable<String> transformWithLatestMode(Observable<String> input, Observable<Mode> mode) {
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * The returned observable emits the values of the input that emits of the
     * fastest observable. The fastest observable is the observable that emits
     * its first item before the other observable emits its first item.
     */
    public <T> Observable<T> fastestWins(Observable<T> observable1, Observable<T> observable2) {
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * This method indexes the input.
     *
     * For example:
     * [a, c, d] is transformed to [(0,a), (1,b), (2,d)]
     *
     * An input with more than Integer.MAX_VALUE items results in
     * undefined behaviour.
     */
    public Observable<Pair<Integer, String>> index(Observable<String> input) {
        //TODO
        throw new UnsupportedOperationException();
    }
}
