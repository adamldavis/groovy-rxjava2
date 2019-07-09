package com.adamldavis.grx2

import io.reactivex.schedulers.Schedulers
import spock.lang.Shared
import spock.lang.Specification

class ExtensionSpec extends Specification {

    @Shared
    List<Integer> list = [1,2,3,4,5,6]

    def "should have toFlowable"() {
        expect:
        list.toFlowable()
    }

    def "should have toObservable"() {
        expect:
        list.toObservable()
    }

    def "Flowable should be able to use Groovy style methods"() {
        def results = []
        expect:
        list.toFlowable().collect_ { it * 2 }.each_ { results.add it }

        assert results == [2,4,6,8,10,12]
    }

    def "Flowable should be able to use Groovy style find "() {
        expect:
        1 == list.toFlowable().find_ { it % 2 == 1 }
    }

    def "Flowable should be able to use Groovy style findAll"() {
        expect:
        [2,4,6] == list.toFlowable().findAll_ { it % 2 == 0 }.blockingIterable() as List
    }

    def "Flowable should be able to use Groovy style inject"() {
        expect:
        21 == list.toFlowable().inject_(0) { a,b -> a + b}
    }


    def "should have toGroovyFlowable"() {
        expect:
        list.toGroovyFlowable()
    }

    def "should have toGroovyObservable"() {
        expect:
        list.toGroovyObservable()
    }

    def "should have io method"() {
        expect:
        list.toFlowable().io().map { it * 2 }.subscribe()
    }

    def "should have computation method"() {
        expect:
        list.toFlowable().computation().map { it * 2 }.subscribe()
    }

    def "should have singleThread method"() {
        expect:
        list.toFlowable().singleThread().map { it * 2 }.subscribe()
    }

    def "should have parallelMap method"() {
        expect:
        list.toFlowable().parallelMap { it ^ 2 }.subscribe()
    }
    def "should have parallelMap(Scheduler, Function) method"() {
        expect:
        list.toFlowable().parallelMap(Schedulers.io()) { it ^ 2 }.subscribe()

    }

}
