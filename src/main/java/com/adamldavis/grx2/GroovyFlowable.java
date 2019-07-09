package com.adamldavis.grx2;

import groovy.lang.Closure;
import groovy.transform.stc.ClosureParams;
import groovy.transform.stc.FromAbstractTypeMethods;
import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

import java.util.Iterator;

public class GroovyFlowable<T> implements Iterable<T> {

    private final Flowable<T> flowable;


    public GroovyFlowable(Flowable<T> flowable) {
        this.flowable = flowable;
    }

    public Flowable<T> getFlowable() {
        return flowable;
    }

    /**
     * Calls {@link io.reactivex.Flowable#map(io.reactivex.functions.Function)}.
     */
    public <T2> GroovyFlowable<T2> collect(@ClosureParams(value = FromAbstractTypeMethods.class) Closure<T2> closure) {

        return new GroovyFlowable<>( flowable.map((T it) -> closure.call(it)) );
    }

    /**
     * Calls {@link io.reactivex.Flowable#map(io.reactivex.functions.Function)}.
     */
    public GroovyFlowable<T> findAll(@ClosureParams(value = FromAbstractTypeMethods.class) Closure<Boolean> closure) {

        return new GroovyFlowable<>( flowable.filter(it -> closure.call(it)) );
    }

    /**
     * Calls {@link io.reactivex.Flowable#filter(Predicate)}.
     */
    public T find(@ClosureParams(value = FromAbstractTypeMethods.class) Closure<Boolean> closure) {

        return flowable.filter(it -> closure.call(it)).firstOrError().blockingGet();
    }

    /**
     * Calls {@link io.reactivex.Flowable#reduce(Object, BiFunction)}.
     */
    public <T2> T2 inject(T2 initial, @ClosureParams(value = FromAbstractTypeMethods.class) Closure<T2> closure) {

        return flowable.reduce(initial, (a, b) -> closure.call(a, b)).blockingGet();
    }

    /**
     * Calls {@link io.reactivex.Flowable#forEach(Consumer)}.
     */
    public void each(@ClosureParams(value = FromAbstractTypeMethods.class) Closure<Object> closure) {

        flowable.forEach(it -> closure.call(it));
    }

    @Override
    public Iterator<T> iterator() {
        return flowable.blockingIterable().iterator();
    }

    @Override
    public void forEach(java.util.function.Consumer<? super T> action) {
        flowable.blockingIterable().forEach(action);
    }
}
