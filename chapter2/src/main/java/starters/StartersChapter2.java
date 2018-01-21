package starters;

import rx.Observable;
import rx.Observer;

public class StartersChapter2 {
    /*
     * Task 1: Counting events
     */
    private int nbOfEvents = 0;
    private boolean completed = false;
    private Throwable error = null;

    public void observe(Observable<?> observable) {
        observable.subscribe(
                e -> nbOfEvents++,
                e -> error = e,
                () -> completed = true
        );
    }

    public int getNbOfEvents() {
        return nbOfEvents;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Throwable getError() {
        return error;
    }

    /*
     * Task 2: Factory methods
     */

    /**
     * Returns an Observable that emits a single item and then completes.
     */
    public static <T> Observable<T> just(final T value) {
        return Observable.create(sub -> {
            sub.onNext(value);
            sub.onCompleted();
        });
    }

    /**
     * Converts an {@link Iterable} sequence into an Observable that emits the items in the sequence.
     */
    public static <T> Observable<T> from(Iterable<? extends T> iterable) {        //TODO
        return Observable.create(sub -> {
            iterable.forEach(sub::onNext);
            sub.onCompleted();
        });
    }

    /**
     * Returns an Observable that emits a sequence of Integers within a specified range.
     *
     * @throws IllegalArgumentException
     *             if {@code count} is less than zero
     */
    public static Observable<Integer> range(int start, int count) {
        if(count < 0) {
            throw new IllegalArgumentException();
        }

        return Observable.create(sub -> {
            for(int i = 0; i < count; i++) {
                sub.onNext(start + i);
            }

            sub.onCompleted();
        });
    }

    /**
     * Returns an Observable that emits no items to the {@link Observer} and immediately invokes its
     * {@link Observer#onCompleted onCompleted} method.
     */
    public static <T> Observable<T> empty() {
        return Observable.create(Observer::onCompleted);
    }

    /**
     * Returns an Observable that never sends any items or notifications to an {@link Observer}.
     */
    public static <T> Observable<T> never() {
        return Observable.create(sub -> {});
    }

    /**
     * Returns an Observable that invokes an {@link Observer}'s {@link Observer#onError onError} method when the
     * Observer subscribes to it.
     */
    public static <T> Observable<T> error(Throwable exception) {
        return Observable.create(sub -> sub.onError(exception));
    }
}
