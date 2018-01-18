package starters;

import org.junit.Test;
import rx.Observable;
import rx.observers.TestSubscriber;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class StartersChapter2Test {
    private StartersChapter2 starters = new StartersChapter2();
    private TestSubscriber<Integer> testSubscriber = TestSubscriber.create();

    /**
     * Task 1
     *
     * Your task:
     * - Implement StartersChapter2.observe
     *
     * The class StartersChapter2 counts the number of events in an observable.
     * To count the number of events, pass the observable to StartersChapter2.observe().
     * You can then retrieve the count with StartersChapter2.getNbOfEvents()
     *
     * You can also check if the observable has completed with StartersChapter2.isCompleted()
     *
     * Stream can also fail. The error can be retrieved with StartersChapter2.getError().
     * It will return null when there were no errors.
     *
     * Hints:
     * - You will need to subscribe to the observable
     */
    @Test
    public void countThreeEvents() throws Exception {
        starters.observe(Observable.just(1, 2, 3));

        assertThat(starters.getNbOfEvents()).isEqualTo(3);
        assertThat(starters.isCompleted()).isTrue();
        assertThat(starters.getError()).isEqualTo(null);
    }

    @Test
    public void countZeroEvents() throws Exception {
        starters.observe(Observable.empty());

        assertThat(starters.getNbOfEvents()).isEqualTo(0);
        assertThat(starters.isCompleted()).isTrue();
        assertThat(starters.getError()).isEqualTo(null);
    }

    @Test
    public void handleErrors() throws Exception {
        RuntimeException myException = new RuntimeException("MyException");

        starters.observe(Observable.error(myException));

        assertThat(starters.getNbOfEvents()).isEqualTo(0);
        assertThat(starters.isCompleted()).isFalse();
        assertThat(starters.getError()).isEqualTo(myException);
    }

    @Test
    public void handleTwoEventsAndThenAnError() throws Exception {
        RuntimeException myException = new RuntimeException("MyException");

        starters.observe(Observable.concat(
                Observable.just(1,2),
                Observable.error(myException)
        ));

        assertThat(starters.getNbOfEvents()).isEqualTo(2);
        assertThat(starters.isCompleted()).isFalse();
        assertThat(starters.getError()).isEqualTo(myException);
    }

    /**
     * Task 2
     *
     * Your task:
     * - Implement the factory methods in StartersChapter2 with Observable.create()
     *
     * StartersChapter2 implements a few factory methods to create Observables.
     * This list of methods happens to be the same list as mentioned in the
     * book, what a coincidence.
     *
     * See the javadoc for each method what they do. They are copied from
     * their counterparts in rx.Observable . Don't be lazy by just using
     * the factories in rx.Observable (except for Observable.create() of course)
     */
    @Test
    public void just_acceptsAnInteger() throws Exception {
        StartersChapter2.just(1)
                .subscribe(testSubscriber);

        testSubscriber.assertValue(1);
        testSubscriber.assertCompleted();
    }

    @Test
    public void just_acceptsNull() throws Exception {
        StartersChapter2.just((Integer)null)
                .subscribe(testSubscriber);

        testSubscriber.assertValue(null);
        testSubscriber.assertCompleted();
    }

    @Test
    public void from_acceptsIntegers() throws Exception {
        StartersChapter2.from(Arrays.asList(1, 2))
                .subscribe(testSubscriber);

        testSubscriber.assertValues(1, 2);
        testSubscriber.assertCompleted();
    }

    @Test
    public void from_acceptsAMixOfIntegersAndNull() throws Exception {
        StartersChapter2.from(Arrays.asList(1, null, 2))
                .subscribe(testSubscriber);

        testSubscriber.assertValues(1, null, 2);
        testSubscriber.assertCompleted();
    }

    @Test
    public void range() throws Exception {
        StartersChapter2.range(3, 5)
                .subscribe(testSubscriber);

        testSubscriber.assertValues(3, 4, 5, 6, 7);
        testSubscriber.assertCompleted();
    }

    @Test
    public void range_acceptsCountZero() throws Exception {
        StartersChapter2.range(5, 0)
                .subscribe(testSubscriber);

        testSubscriber.assertNoValues();
        testSubscriber.assertCompleted();
    }

    @Test(expected = IllegalArgumentException.class)
    public void range_doesNotAcceptANegativeCount() throws Exception {
        StartersChapter2.range(0, -1);
    }

    @Test
    public void empty() throws Exception {
        StartersChapter2.<Integer>empty()
                .subscribe(testSubscriber);

        testSubscriber.assertNoValues();
        testSubscriber.assertCompleted();
    }

    @Test
    public void never_doesNotComplete() throws Exception {
        testSubscriber.awaitTerminalEvent(1, TimeUnit.SECONDS);

        StartersChapter2.<Integer>never()
                .subscribe(testSubscriber);

        testSubscriber.assertNotCompleted();
    }

    @Test
    public void error() throws Exception {
        RuntimeException myException = new RuntimeException("MyException");

        StartersChapter2.<Integer>error(myException)
                .subscribe(testSubscriber);

        testSubscriber.assertError(myException);
    }

    /*
     * Congratulations, you finished the starters for chapter 2.
     *
     * Go to ClockControllerTest to start with the project.
     */
}