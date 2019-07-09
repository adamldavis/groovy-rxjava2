package com.adamldavis.grx2

import spock.lang.Shared
import spock.lang.Specification

class GroovyClassesSpec extends Specification {

    @Shared
    List<Integer> list = [1,2,3,4,5,6]


    def "GroovyFlowable should be able to use Groovy style collect"() {
        expect:
        list.toGroovyFlowable().collect { it * 2 } as List == [2,4,6,8,10,12]
    }

    def "GroovyFlowable should be able to use getFlowable"() {
        expect:
        list.toGroovyFlowable().collect { it * 2 }.getFlowable().blockingIterable() as List == [2,4,6,8,10,12]
    }

    def "GroovyFlowable should be able to use Groovy style methods"() {
        def results = []
        expect:
        list.toGroovyFlowable().collect { it * 2 }.each { results.add it }

        assert results == [2,4,6,8,10,12]
    }

    def "GroovyFlowable should be able to use Groovy style find "() {
        expect:
        1 == list.toGroovyFlowable().find { it % 2 == 1 }
    }

    def "GroovyFlowable should be able to use Groovy style findAll"() {
        expect:
        [2,4,6] == list.toGroovyFlowable().findAll { it % 2 == 0 } as List
    }

    def "GroovyFlowable should be able to use Groovy style inject"() {
        expect:
        21 == list.toGroovyFlowable().inject(0) { a,b -> a + b}
    }
    
    //--------------Observables

    def "GroovyObservable should be able to use Groovy style collect"() {
        expect:
        list.toGroovyObservable().collect { it * 2 } as List == [2,4,6,8,10,12]
    }

    def "GroovyObservable should be able to use getObservable"() {
        expect:
        list.toGroovyObservable().collect { it * 2 }.getObservable().blockingIterable() as List == [2,4,6,8,10,12]
    }

    def "GroovyObservable should be able to use Groovy style methods"() {
        def results = []
        expect:
        list.toGroovyObservable().collect { it * 2 }.each { results.add it }

        assert results == [2,4,6,8,10,12]
    }

    def "GroovyObservable should be able to use Groovy style find "() {
        expect:
        1 == list.toGroovyObservable().find { it % 2 == 1 }
    }

    def "GroovyObservable should be able to use Groovy style findAll"() {
        expect:
        [2,4,6] == list.toGroovyObservable().findAll { it % 2 == 0 } as List
    }

    def "GroovyObservable should be able to use Groovy style inject"() {
        expect:
        21 == list.toGroovyObservable().inject(0) { a,b -> a + b}
    }


}
