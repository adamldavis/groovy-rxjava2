package com.adamldavis.grx2;

import groovy.lang.Closure;
import groovy.transform.stc.ClosureParams;
import groovy.transform.stc.FromAbstractTypeMethods;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

import java.util.Iterator;

public class GroovyObservable<T>  implements Iterable<T>{

    private final Observable<T> observable;

    public GroovyObservable(Observable<T> observable) {
        this.observable = observable;
    }

    public Observable<T> getObservable() {
        return observable;
    }

    /**
     * Calls {@link io.reactivex.Observable#map(io.reactivex.functions.Function)}.
     */
    public <T2> GroovyObservable<T2> collect(@ClosureParams(value = FromAbstractTypeMethods.class) Closure<T2> closure) {

        return new GroovyObservable<>( observable.map((T it) -> closure.call(it)) );
    }

    /**
     * Calls {@link io.reactivex.Observable#map(io.reactivex.functions.Function)}.
     */
    public GroovyObservable<T> findAll(@ClosureParams(value = FromAbstractTypeMethods.class) Closure<Boolean> closure) {

        return new GroovyObservable<>( observable.filter(it -> closure.call(it)) );
    }

    /**
     * Calls {@link io.reactivex.Observable#filter(Predicate)}.
     */
    public T find(@ClosureParams(value = FromAbstractTypeMethods.class) Closure<Boolean> closure) {

        return observable.filter(it -> closure.call(it)).firstOrError().blockingGet();
    }

    /**
     * Calls {@link io.reactivex.Observable#reduce(Object, BiFunction)}.
     */
    public <T2> T2 inject(T2 initial, @ClosureParams(value = FromAbstractTypeMethods.class) Closure<T2> closure) {

        return observable.reduce(initial, (a, b) -> closure.call(a, b)).blockingGet();
    }

    /**
     * Calls {@link io.reactivex.Observable#forEach(Consumer)}.
     */
    public void each(@ClosureParams(value = FromAbstractTypeMethods.class) Closure<Object> closure) {

        observable.forEach(it -> closure.call(it));
    }

    @Override
    public Iterator<T> iterator() {
        return observable.blockingIterable().iterator();
    }

    @Override
    public void forEach(java.util.function.Consumer<? super T> action) {
        observable.blockingIterable().forEach(action);
    }
}
