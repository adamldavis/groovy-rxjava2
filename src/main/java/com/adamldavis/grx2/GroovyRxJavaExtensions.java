package com.adamldavis.grx2;

import io.reactivex.*;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Publisher;

public final class GroovyRxJavaExtensions {

    /** Calls {@link io.reactivex.Flowable#fromIterable(java.lang.Iterable)}. */
    public static <T> Flowable<T> toFlowable(Iterable<T> it) {

        return Flowable.fromIterable(it);
    }

    /** Calls {@link io.reactivex.Observable#fromIterable(java.lang.Iterable)}. */
    public static <T> Observable<T> toObservable(Iterable<T> it) {

        return Observable.fromIterable(it);
    }

    /** Calls {@link io.reactivex.Flowable#fromIterable(java.lang.Iterable)} and new GroovyFlowable.
     *@see GroovyFlowable */
    public static <T> GroovyFlowable<T> toGroovyFlowable(Iterable<T> it) {

        return new GroovyFlowable<>(Flowable.fromIterable(it));
    }

    /** Calls {@link io.reactivex.Observable#fromIterable(java.lang.Iterable)} and new GroovyObservable.
     *@see GroovyObservable */
    public static <T> GroovyObservable<T> toGroovyObservable(Iterable<T> it) {

        return new GroovyObservable<>(Observable.fromIterable(it));
    }


    /** Calls  new GroovyFlowable.
     *@see GroovyFlowable */
    public static <T> GroovyFlowable<T> toGroovyFlowable(Flowable<T> it) {

        return new GroovyFlowable<>(it);
    }

    /** Calls  new GroovyObservable.
     *@see GroovyObservable */
    public static <T> GroovyObservable<T> toGroovyObservable(Observable<T> it) {

        return new GroovyObservable<>(it);
    }


    /** Calls {@link io.reactivex.Flowable#map(io.reactivex.functions.Function)}. */
    public static <T1, T2> Flowable<T2> collect_(Flowable<T1> flowable, Function<? super T1, ? extends T2> function) {
        return flowable.map(function);
    }

    /** Calls {@link io.reactivex.Flowable#filter(Predicate)}. */
    public static <T1> Flowable<T1> findAll_(Flowable<T1> flowable, Predicate<? super T1> predicate) {

        return flowable.filter(predicate);
    }

    /** Calls {@link io.reactivex.Flowable#filter(Predicate)} and {@link Flowable#firstOrError()}. */
    public static <T1> T1 find_(Flowable<T1> flowable, Predicate<? super T1> predicate) {

        return flowable.filter(predicate).firstOrError().blockingGet();
    }

    /** Calls {@link io.reactivex.Flowable#reduce(Object, BiFunction)}. */
    public static <T1, T2> T2 inject_(Flowable<T1> flowable, T2 initial, BiFunction<T2, ? super T1, T2> function) {
        return flowable.reduce(initial, function).blockingGet();
    }

    /** Calls {@link io.reactivex.Flowable#forEach(Consumer)}. */
    public static <T1> void each_(Flowable<T1> flowable, Consumer<T1> consumer) {

        flowable.forEach(consumer);
    }

    /** Calls {@link io.reactivex.Flowable#doOnNext(Consumer)} . */
    public static <T1> void peek(Flowable<T1> flowable, Consumer<T1> consumer) {

        flowable.doOnNext(consumer);
    }

    /** Calls {@link io.reactivex.Flowable#observeOn(Scheduler)} with {@link Schedulers#io()}  scheduler. */
    public static <T> Flowable<T> io(Flowable<T> it) {

        return it.observeOn(Schedulers.io());
    }

    /** Calls {@link io.reactivex.Flowable#observeOn(Scheduler)} with {@link Schedulers#computation()} ()}  scheduler.*/
    public static <T> Flowable<T> computation(Flowable<T> it) {

        return it.observeOn(Schedulers.computation());
    }

    /** Calls {@link io.reactivex.Flowable#observeOn(Scheduler)} with {@link Schedulers#single()} scheduler. */
    public static <T> Flowable<T> singleThread(Flowable<T> it) {

        return it.observeOn(Schedulers.single());
    }

    /**
     * Same as flowable.parallel().runOn(Schedulers.computation()).map(function).sequential().
     *
     * @see Flowable#parallel()
     */
    public static <T1, T2> Flowable<? extends T2> parallelMap(Flowable<T1> flowable,
                                                              Function<? super T1, ? extends T2> function) {

        return parallelMap(flowable, Schedulers.computation(), function);
    }

    /**
     * Same as flowable.parallel().runOn(scheduler).map(function).sequential().
     *
     * @see Flowable#parallel()
     */
    public static <T1, T2> Flowable<? extends T2> parallelMap(Flowable<T1> flowable,
                                             Scheduler scheduler,
                                             Function<? super T1, ? extends T2> function) {

        return flowable.parallel().runOn(scheduler).map(function).sequential();
    }

    /** Calls {@link io.reactivex.Flowable#onErrorResumeNext(io.reactivex.functions.Function)}.
     * The given Closure should take in a Throwable and return a new Publisher such as a new Flowable.
     * */
    public static <T> Flowable<T> onErrorResume(Flowable<T> flowable,
                                         Function<? super Throwable, ? extends Publisher<? extends T>> function) {
        return flowable.onErrorResumeNext(function);
    }

    /** Calls {@link io.reactivex.Flowable#onErrorResumeNext(Publisher)}. */
    public static <T> Flowable<T> onErrorResumeWithPublisher(Flowable<T> flowable, Publisher<? extends T> publisher) {
        return flowable.onErrorResumeNext(publisher);
    }

}
