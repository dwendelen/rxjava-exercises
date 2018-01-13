package starters;

import rx.Observable;
import rx.Observer;

public class Starters1 {
    /*
     * Task 1: Counting events
     */
    private int nbOfEvents = 0;
    private boolean completed = false;
    private Throwable error = null;

    public void observe(Observable<?> observable) {
        //TODO
        throw new UnsupportedOperationException();
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
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * Converts an {@link Iterable} sequence into an Observable that emits the items in the sequence.
     */
    public static <T> Observable<T> from(Iterable<? extends T> iterable) {        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an Observable that emits a sequence of Integers within a specified range.
     *
     * @throws IllegalArgumentException
     *             if {@code count} is less than zero
     */
    public static Observable<Integer> range(int start, int count) {        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an Observable that emits no items to the {@link Observer} and immediately invokes its
     * {@link Observer#onCompleted onCompleted} method.
     */
    public static <T> Observable<T> empty() {
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an Observable that never sends any items or notifications to an {@link Observer}.
     */
    public static <T> Observable<T> never() {
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an Observable that invokes an {@link Observer}'s {@link Observer#onError onError} method when the
     * Observer subscribes to it.
     */
    public static <T> Observable<T> error(Throwable exception) {
        //TODO
        throw new UnsupportedOperationException();
    }
}
