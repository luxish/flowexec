package org.luxish.flowexec;

import org.junit.jupiter.api.Test;

import java.util.Random;

public class FlowTest {

    @Test
    public void testFlow() {
        new Flow().create()
            .sequence()
                .add(new SequnceGenericAction())
                .add(new SequnceGenericAction())
                .handler((result) -> System.out.println("Handle result=" +((SequenceResult)result).result()))
            .then()
            .parallel()
                .add(new ParallelGenericAction())
                .add(new ParallelGenericAction())
                .add(new ParallelGenericAction())
                .add(new ParallelGenericAction())
                .add(new ParallelGenericAction())
                .add(new ParallelGenericAction())
                .handler((result) -> System.out.println("Handle result=" + ((ParallelResult)result).result()))
            .then()
            .stop()
            .execute();
    }

    private static class SequnceGenericAction implements GenericAction {
        @Override
        public GenericActionResult exec() {
            System.out.printf("Sequence start thread='%s'%n", Thread.currentThread().getName());
            try {
                Thread.sleep(new Random().nextLong(1000, 2000));
            } catch (InterruptedException e) {
                //
            }
            System.out.printf("Sequence end thread='%s'%n", Thread.currentThread().getName());
            return new SequenceResult("Done " + Thread.currentThread().getName());
        }
    }

    private static class ParallelGenericAction implements GenericAction {
        @Override
        public GenericActionResult exec() {
            System.out.printf("Parallel start %s %n", Thread.currentThread().getName());
            try {
                Thread.sleep(new Random().nextLong(1000, 10000));
            } catch (InterruptedException e) {
                //
            }
            System.out.printf("Parallel end thread='%s'%n", Thread.currentThread().getName());
            return new ParallelResult("Done " + Thread.currentThread().getName());
        }
    }

    private record SequenceResult(String result) implements GenericActionResult{}
    private record ParallelResult(String result) implements GenericActionResult{}
}
