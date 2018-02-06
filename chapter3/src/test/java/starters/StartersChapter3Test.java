package starters;

import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StartersChapter3Test {
    private TestScheduler scheduler = Schedulers.test();
    private SomeDataSource someDataSource = mock(SomeDataSource.class);

    private StartersChapter3 startersChapter3 = new StartersChapter3(scheduler, someDataSource);

    private TestSubscriber<Integer> integerSubscriber = TestSubscriber.create();
    private TestSubscriber<String> stringSubscriber = TestSubscriber.create();
    private TestSubscriber<SomeData> someDataSubscriber = TestSubscriber.create();
    private TestSubscriber<Pair<Integer, String>> pairSubscriber = TestSubscriber.create();
    private TestSubscriber<Configuration> configurationSubscriber = TestSubscriber.create();

    private static final SomeData DATA_1 = new SomeData("1");
    private static final SomeData DATA_2 = new SomeData("2");
    private static final SomeData DATA_3 = new SomeData("3");

    @Before
    public void setUp() {
        mockData(DATA_1, 1);
        mockData(DATA_2, 2);
        mockData(DATA_3, 3);
    }

    /*
     * Task 1
     *
     * Your task:
     * - Implement the following methods of StartersChapter3:
     *     - toUpper()
     *     - toString()
     *     - even()
     *     - flattenEager()
     *     - flattenInOrder()
     *     - loadMany()
     *     - loadManyOneByOne()
     *     - loadManyWithLimitedConcurrency()
     * - Using exactly one of the following transformations:
     *     - Observable.concatMap()
     *     - Observable.filter()
     *     - Observable.flatMap()
     *     - Observable.map()
     *
     * The listed methods of StartersChapter3 can be implemented with one of
     * the listed transformations. It is not necessary to combine multiple
     * transformations.
     *
     * The details of the expected behaviour can be found in the javadoc of the
     * method that needs to be implemented.
     */
    @Test
    public void toUpper_convertsStrings() {
        Observable<String> someStrings = Observable.just("", "a", "A", "Dakke", "4");

        startersChapter3.toUpper(someStrings)
                .subscribe(stringSubscriber);

        stringSubscriber.assertValues("", "A", "A", "DAKKE", "4");
        stringSubscriber.assertCompleted();
    }

    @Test
    public void toString_convertsItemsToTheirStringRepresentation() {
        Observable<Integer> input = Observable.just(1, 8, 4);

        startersChapter3.toString(input)
                .subscribe(stringSubscriber);

        stringSubscriber.assertValues("1", "8", "4");
        stringSubscriber.assertCompleted();
    }

    @Test
    public void even_onlyKeepsTheEvenNumbersInTheStream() {
        Observable<Integer> input = Observable.just(1, 8, 9, 5, 4, 3, -5, -4, 0);

        startersChapter3.even(input)
                .subscribe(integerSubscriber);

        integerSubscriber.assertValues(8, 4, -4, 0);
        integerSubscriber.assertCompleted();
    }

    @Test
    public void flattenEager_flattensTheObservable() {
        Observable<Observable<Integer>> input = Observable.just(
                Observable.just(1),
                Observable.just(4, -2),
                Observable.just(3, 6)
        );

        startersChapter3.flattenEager(input)
                .subscribe(integerSubscriber);

        assertThat(integerSubscriber.getOnNextEvents())
                .containsOnly(-2, 3, 1, 6, 4);
        integerSubscriber.assertCompleted();
    }

    @Test
    public void flattenEager_isEager() {
        Observable<Observable<Integer>> input = Observable.just(
                delayed(2, 2),
                delayed(3, 3),
                delayed(1, 1)
        );

        startersChapter3.flattenEager(input)
                .subscribe(integerSubscriber);

        scheduler.advanceTimeBy(3, TimeUnit.MILLISECONDS);

        integerSubscriber.assertValues(1, 2, 3);
        integerSubscriber.assertCompleted();
    }

    @Test
    public void flattenInOrder_respectsTheOrderTheInnerAndOuterObservable() {
        Observable<Observable<Integer>> input = Observable.just(
                Observable.just(1),
                Observable.just(4, -2),
                Observable.just(3, 6)
        );

        startersChapter3.flattenInOrder(input)
                .subscribe(integerSubscriber);

        integerSubscriber.assertValues(1, 4, -2, 3, 6);
        integerSubscriber.assertCompleted();
    }

    @Test
    public void loadMany_loadsDataWhenAnIdIsEmitted() {
        Observable<String> input = Observable.just("1", "3", "2");

        startersChapter3.loadMany(input)
                .subscribe(someDataSubscriber);

        scheduler.advanceTimeBy(3, TimeUnit.MILLISECONDS);

        someDataSubscriber.assertValues(DATA_1, DATA_2, DATA_3);
        someDataSubscriber.assertCompleted();
    }

    @Test
    public void loadManyOneByOne_loadsTheDataOneAtATime() {
        Observable<String> input = Observable.just("1", "3", "2");

        startersChapter3.loadManyOneByOne(input)
                .subscribe(someDataSubscriber);

        scheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS);
        someDataSubscriber.assertValues(DATA_1);

        scheduler.advanceTimeBy(3, TimeUnit.MILLISECONDS);
        someDataSubscriber.assertValues(DATA_1, DATA_3);

        scheduler.advanceTimeBy(2, TimeUnit.MILLISECONDS);
        someDataSubscriber.assertValues(DATA_1, DATA_3, DATA_2);
        someDataSubscriber.assertCompleted();
    }

    @Test
    public void loadManyLimitedConcurrency_loadsOnlyTwoPiecesOfDataAtATime() {
        Observable<String> input = Observable.just("3", "1", "2");

        startersChapter3.loadManyWithLimitedConcurrency(input, 2)
                .subscribe(someDataSubscriber);

        scheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS);
        someDataSubscriber.assertValues(DATA_1);

        scheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS);
        someDataSubscriber.assertValues(DATA_1);

        scheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS);
        someDataSubscriber.assertValues(DATA_1, DATA_3, DATA_2);
        someDataSubscriber.assertCompleted();
    }

    /*
     * Task 2
     *
     * Your task:
     * - Implement the following methods of StartersChapter3:
     *     - allPositiveOddNumbersSmallerThan()
     *     - evenAsString()
     *     - sleepSort()
     *     - compoundedSleep()
     * - Using a combination of the following transformations:
     *     - Observable.concatMap()
     *     - Observable.delay()
     *     - Observable.filter()
     *     - Observable.flatMap()
     *     - Observable.map()
     * - And/or in combination with the factory methods studied in chapter 2
     *
     * The listed methods of StartersChapter3 can be implemented with one of
     * the listed transformations. For some methods you also need to use one
     * of the factory methods studied in chapter 2.
     *
     * The details of the expected behaviour can be found in the javadoc of the
     * method that needs to be implemented.
     */
    @Test
    public void allPositiveOddNumbersSmallerThan() {
        startersChapter3.allPositiveOddNumbersSmallerThan(11)
                .subscribe(integerSubscriber);

        integerSubscriber.assertValues(1, 3, 5, 7, 9);
        integerSubscriber.assertCompleted();
    }

    @Test
    public void evenAsString() {
        Observable<Integer> input = Observable.just(1, 8, 9, 5, 4, 3, -5, -4, 0);

        startersChapter3.evenAsString(input)
                .subscribe(stringSubscriber);

        stringSubscriber.assertValues("8", "4", "-4", "0");
        stringSubscriber.assertCompleted();
    }

    @Test
    public void sleepSort() {
        Iterable<Integer> input = Arrays.asList(3, 1, 2);

        startersChapter3.sleepSort(input)
                .subscribe(integerSubscriber);

        scheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS);
        integerSubscriber.assertValues(1);

        scheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS);
        integerSubscriber.assertValues(1, 2);

        scheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS);
        integerSubscriber.assertValues(1, 2, 3);
        integerSubscriber.assertCompleted();
    }

    @Test
    public void compoundedSleep() {
        Iterable<Integer> input = Arrays.asList(3, 1, 2);

        startersChapter3.compoundedSleep(input)
                .subscribe(integerSubscriber);

        scheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS);
        integerSubscriber.assertValues();

        scheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS);
        integerSubscriber.assertValues();

        scheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS);
        integerSubscriber.assertValues(3);

        scheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS);
        integerSubscriber.assertValues(3, 1);

        scheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS);
        integerSubscriber.assertValues(3, 1);

        scheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS);
        integerSubscriber.assertValues(3, 1, 2);
        integerSubscriber.assertCompleted();
    }

    /*
     * Task 3
     *
     * Your task:
     * - Implement the following methods of StartersChapter3:
     *     - allName
     *     - pair
     *     - configuration
     *     - transformWithLatestMode
     *     - fastestWins
     *     - index
     * - Using some of the following transformations:
     *     - Observable.amb()
     *     - Observable.combineLatest()
     *     - Observable.merge()
     *     - Observable.withLatestFrom()
     *     - Observable.zip()
     *
     * The listed methods of StartersChapter3 can be implemented with one or
     * more of the listed transformations.
     *
     * The details of the expected behaviour can be found in the javadoc of the
     * method that needs to be implemented.
     */

    @Test
    public void allNames_emitsMaleAndFemaleNames() {
        Observable<String> maleNames = Observable.just("Tor", "Oden", "Loke");
        Observable<String> femaleNames = Observable.just("Freja", "Siv", "Sigyn");

        startersChapter3.allName(maleNames, femaleNames)
                .subscribe(stringSubscriber);

        assertThat(stringSubscriber.getOnNextEvents())
                .containsOnly("Tor", "Oden", "Loke", "Freja", "Siv", "Sigyn");
        stringSubscriber.assertCompleted();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void pair_pairsCorrespondingItemsTogether() {
        Observable<Integer> integers = Observable.just(3, 5, 2);
        Observable<String> strings = Observable.just("a", "b", "c");

        startersChapter3.pair(integers, strings)
                .subscribe(pairSubscriber);

        pairSubscriber.assertValues(
                new Pair<>(3, "a"),
                new Pair<>(5, "b"),
                new Pair<>(2, "c")
        );
        pairSubscriber.assertCompleted();
    }

    @Test
    public void configuration_isUpdatedWhenOneOfTheSettingsIsUpdated() {
        Observable<String> setting1_1 = delayed("Setting1_1", 0);
        Observable<String> setting2_1 = delayed("Setting2_1", 1);
        Observable<String> setting1_2 = delayed("Setting1_2", 2);
        Observable<String> setting2_2 = delayed("Setting2_2", 3);
        Observable<String> setting2_3 = delayed("Setting2_3", 4);
        Observable<String> setting1_3 = delayed("Setting1_3", 5);

        Observable<String> setting1 = Observable.merge(setting1_1, setting1_2, setting1_3);
        Observable<String> setting2 = Observable.merge(setting2_1, setting2_2, setting2_3);

        startersChapter3.configuration(setting1, setting2)
                .subscribe(configurationSubscriber);

        scheduler.advanceTimeBy(5, TimeUnit.MILLISECONDS);

        configurationSubscriber.assertValues(
                new Configuration("Setting1_1", "Setting2_1"),
                new Configuration("Setting1_2", "Setting2_1"),
                new Configuration("Setting1_2", "Setting2_2"),
                new Configuration("Setting1_2", "Setting2_3"),
                new Configuration("Setting1_3", "Setting2_3")
        );
        configurationSubscriber.assertCompleted();
    }

    @Test
    public void transformWithLatestMode_transformsTheInputsWithTheLastestMode() {
        Observable<String> string1 = delayed("String1", 0);
        Observable<Mode> mode1 = delayed(Mode.LOWER, 1);
        Observable<String> string2 = delayed("String2", 2);
        Observable<String> string3 = delayed("String3", 3);
        Observable<Mode> mode2 = delayed(Mode.UPPER, 4);
        Observable<Mode> mode3 = delayed(Mode.AS_IS, 5);
        Observable<String> string4 = delayed("String4", 6);


        Observable<String> inputs = Observable.merge(string1, string2, string3, string4);
        Observable<Mode> modes = Observable.merge(mode1, mode2, mode3);

        startersChapter3.transformWithLatestMode(inputs, modes)
                .subscribe(stringSubscriber);

        scheduler.advanceTimeBy(6, TimeUnit.MILLISECONDS);

        stringSubscriber.assertValues(
                "string2",
                "string3",
                "String4"
        );
        stringSubscriber.assertCompleted();
    }

    @Test
    public void fastestWins_theFastestObservableWins() {
        Observable<Integer> slow = Observable.just(1, 2, 3).delay(2, TimeUnit.MILLISECONDS);
        Observable<Integer> fast = Observable.just(4, 5, 6).delay(1, TimeUnit.MILLISECONDS);

        startersChapter3.fastestWins(slow, fast)
                .subscribe(integerSubscriber);

        integerSubscriber.assertValues(4, 5, 6);
        integerSubscriber.assertCompleted();
    }

    @Test
    public void fastestWins_theFastestObservableAlsoWinsWhenItIsTheSecondArgument() {
        Observable<Integer> slow = Observable.just(1, 2, 3).delay(2, TimeUnit.MILLISECONDS);
        Observable<Integer> fast = Observable.just(4, 5, 6).delay(1, TimeUnit.MILLISECONDS);

        startersChapter3.fastestWins(fast, slow)
                .subscribe(integerSubscriber);

        integerSubscriber.assertValues(4, 5, 6);
        integerSubscriber.assertCompleted();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void index_addsAnIndexToEveryString() {
        Observable<String> inputs = Observable.just(
                "String1",
                "String2",
                "String3"
        );

        startersChapter3.index(inputs)
                .subscribe(pairSubscriber);

        pairSubscriber.assertValues(
                new Pair<>(0, "String1"),
                new Pair<>(1, "String2"),
                new Pair<>(2, "String3")
        );
        pairSubscriber.assertCompleted();
    }

    //TODO add task and javadoc
    /*
     * Task 4
     *
     * Your task:
     * - Implement the following methods of StartersChapter3:
     *     - sum
     *     - continuousSum
     * - Using some of the following transformations:
     *     - Observable.reduce()
     *     - Observable.scan()
     *
     * The details of the expected behaviour can be found in the javadoc of the
     * method that needs to be implemented.
     */

    @Test
    public void sum_calculatesTheSumOfTheInput() {
        Observable<Integer> input = Observable.just(1, 4, 7);

        startersChapter3.sum(input)
                .subscribe(integerSubscriber);

        integerSubscriber.assertValue(12);
        integerSubscriber.assertCompleted();
    }

    @Test
    public void continuousSum_continuouslyCalculatesTheSumAndEmitsIntermediateResults() {
        Observable<Integer> input = Observable.just(1, 4, 7);

        startersChapter3.continuousSum(input)
                .subscribe(integerSubscriber);

        integerSubscriber.assertValues(0, 1, 5, 12);
        integerSubscriber.assertCompleted();
    }

    /*
     * Task 5
     *
     * Your task:
     * - Implement the following methods of StartersChapter3:
     *     - selectSetting1FromConfigurations
     *     - selectConfigurationWhereSetting1EqualsBla
     *     - selectConfigurationLimit5
     *     - selectConfigurationSkip3
     *     - selectDistinctSetting1
     *     - selectCount(configurations)
     *     - selectSetting2WhereSetting1EqualsBlaLimit1(configurations)
     *     - selectSetting1GroupBySetting1(configurations)
     *     - selectCountSetting1GroupBySetting1(configurations)
     *     - selectCountSetting1GroupBySetting1HavingCountGreaterThan1(configurations)
     * - Using some of the following transformations:
     *     - Observable.reduce()
     *     - Observable.scan()
     *
     * The details of the expected behaviour can be found in the javadoc of the
     * method that needs to be implemented.
     */

    @Test
    public void selectSetting1FromConfigurations() {
        Observable<Configuration> configurations = Observable.just(
                new Configuration("aaa", "bbb"),
                new Configuration("bbb", "ccc"),
                new Configuration("ccc", "ddd")
        );

        startersChapter3.selectSetting1FromConfigurations(configurations)
                .subscribe(stringSubscriber);

        stringSubscriber.assertValues("aaa", "bbb", "ccc");
        stringSubscriber.assertCompleted();
    }

    @Test
    public void selectConfigurationWhereSetting1EqualsBla() {
        Configuration config1 = new Configuration("bli", "other1");
        Configuration config2 = new Configuration("blo", "other2");
        Configuration config3 = new Configuration("bla", "other3");
        Configuration config4 = new Configuration("bly", "other4");
        Configuration config5 = new Configuration("bla", "other5");

        Observable<Configuration> configurations = Observable.just(
                config1,
                config2,
                config3,
                config4,
                config5
        );

        startersChapter3.selectConfigurationWhereSetting1EqualsBla(configurations)
                .subscribe(configurationSubscriber);

        configurationSubscriber.assertValues(config3, config5);
        configurationSubscriber.assertCompleted();
    }

    @Test
    public void selectConfigurationLimit5() {
        Configuration config1 = new Configuration("1", "other1");
        Configuration config2 = new Configuration("2", "other2");
        Configuration config3 = new Configuration("3", "other3");
        Configuration config4 = new Configuration("4", "other4");
        Configuration config5 = new Configuration("5", "other5");
        Configuration config6 = new Configuration("6", "other6");
        Configuration config7 = new Configuration("7", "other7");

        Observable<Configuration> configurations = Observable.just(
                config1,
                config2,
                config3,
                config4,
                config5,
                config6,
                config7
        );

        startersChapter3.selectConfigurationLimit5(configurations)
                .subscribe(configurationSubscriber);

        configurationSubscriber.assertValues(
                config1,
                config2,
                config3,
                config4,
                config5
        );
        configurationSubscriber.assertCompleted();
    }

    @Test
    public void selectConfigurationSkip3() {
        Configuration config1 = new Configuration("1", "other1");
        Configuration config2 = new Configuration("2", "other2");
        Configuration config3 = new Configuration("3", "other3");
        Configuration config4 = new Configuration("4", "other4");
        Configuration config5 = new Configuration("5", "other5");
        Configuration config6 = new Configuration("6", "other6");
        Configuration config7 = new Configuration("7", "other7");

        Observable<Configuration> configurations = Observable.just(
                config1,
                config2,
                config3,
                config4,
                config5,
                config6,
                config7
        );

        startersChapter3.selectConfigurationSkip3(configurations)
                .subscribe(configurationSubscriber);

        configurationSubscriber.assertValues(
                config4,
                config5,
                config6,
                config7
        );
        configurationSubscriber.assertCompleted();
    }

    @Test
    public void selectDistinctSetting1() {
        Observable<Configuration> configurations = Observable.just(
                new Configuration("bli", "other1"),
                new Configuration("blo", "other2"),
                new Configuration("bla", "other3"),
                new Configuration("bly", "other4"),
                new Configuration("bla", "other5")
        );

        startersChapter3.selectDistinctSetting1(configurations)
                .subscribe(stringSubscriber);

        stringSubscriber.assertValues("bli", "blo", "bla", "bly");
        stringSubscriber.assertCompleted();
    }

    @Test
    public void selectCount() {
        Observable<Configuration> configurations = Observable.just(
                new Configuration("1", "other1"),
                new Configuration("2", "other2"),
                new Configuration("3", "other3"),
                new Configuration("4", "other4")
        );

        startersChapter3.selectCount(configurations)
                .subscribe(integerSubscriber);

        integerSubscriber.assertValue(4);
        integerSubscriber.assertCompleted();
    }

    @Test
    public void selectSetting2WhereSetting1EqualsBlaLimit1() {
        Observable<Configuration> configurations = Observable.just(
                new Configuration("bli", "other1"),
                new Configuration("blo", "other2"),
                new Configuration("bla", "other3"),
                new Configuration("bly", "other4"),
                new Configuration("bla", "other5")
        );

        startersChapter3.selectSetting2WhereSetting1EqualsBlaLimit1(configurations)
                .subscribe(stringSubscriber);

        stringSubscriber.assertValues("other3");
        stringSubscriber.assertCompleted();
    }

    @Test
    public void selectSetting1GroupBySetting1() {
        Observable<Configuration> configurations = Observable.just(
                new Configuration("bli", "other1"),
                new Configuration("blo", "other2"),
                new Configuration("bla", "other3"),
                new Configuration("bly", "other4"),
                new Configuration("bla", "other5")
        );

        startersChapter3.selectSetting1GroupBySetting1(configurations)
                .subscribe(stringSubscriber);

        assertThat(stringSubscriber.getOnNextEvents())
                .containsOnly("bli", "blo", "bla", "bly");
        stringSubscriber.assertCompleted();
    }

    @Test
    public void selectCountSetting1GroupBySetting1() {
        Observable<Configuration> configurations = Observable.just(
                new Configuration("bli", "other1"),
                new Configuration("blo", "other2"),
                new Configuration("bla", "other3"),
                new Configuration("bly", "other4"),
                new Configuration("bla", "other5")
        );

        startersChapter3.selectCountSetting1GroupBySetting1(configurations)
                .subscribe(pairSubscriber);

        assertThat(pairSubscriber.getOnNextEvents()).containsOnly(
                new Pair<>(2, "bla"),
                new Pair<>(1, "blo"),
                new Pair<>(1, "bli"),
                new Pair<>(1, "bly")
        );
        pairSubscriber.assertCompleted();
    }

    @Test
    public void selectCountSetting1GroupBySetting1HavingCountGreaterThan1() {
        Observable<Configuration> configurations = Observable.just(
                new Configuration("bli", "other1"),
                new Configuration("blo", "other2"),
                new Configuration("bla", "other3"),
                new Configuration("bly", "other4"),
                new Configuration("bla", "other5")
        );

        startersChapter3.selectCountSetting1GroupBySetting1HavingCountGreaterThan1(configurations)
                .subscribe(pairSubscriber);

        pairSubscriber.assertValue(new Pair<>(2, "bla"));
        pairSubscriber.assertCompleted();
    }


    private void mockData(SomeData data, int id) {
        Observable<SomeData> observable = delayed(data, id);

        when(someDataSource.load(String.valueOf(id)))
                .thenReturn(observable);
    }

    private <T> Observable<T> delayed(T data, int delay) {
        return Observable.just(data)
                .delay(delay, TimeUnit.MILLISECONDS, scheduler);
    }
}