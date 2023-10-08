package org.luxish.flowexec;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlowTest {

    @Test
    public void testFlowNoState() {
        Flow.create()
            .sequence()
                .add(new SequnceGenericAction())
                .add(new SequnceGenericAction())
                .handler((result, state) -> System.out.println("Handle result= " +((SequenceResult)result).result()))
            .then()
            .parallel()
                .add(new ParallelGenericAction())
                .add(new ParallelGenericAction())
                .add(new ParallelGenericAction())
                .add(new ParallelGenericAction())
                .add(new ParallelGenericAction())
                .add(new ParallelGenericAction())
                .handler((result,state) -> System.out.println("Handle result= " + ((ParallelResult)result).result()))
            .then()
            .stop()
            .execute();
    }

    @Test
    public void testFlowWithState() {
        SharedState stateTest = new SharedState(0);
        Flow.create(stateTest)
            .parallel()
                .add(new ParallelGenericAction())
                .add(new ParallelGenericAction())
                .handler((result, state) -> state.increment() )
            .then()
            .stop()
            .execute();
        assertEquals(2, stateTest.getCounter());
    }


    private static class SequnceGenericAction implements GenericAction {
        @Override
        public GenericActionResult exec() {
            long start = System.currentTimeMillis();
            try {
                Thread.sleep(new Random().nextLong(1000, 2000));
            } catch (InterruptedException e) {
                //
            }
            String message = String.format("Sequence end thread='%s' timeMs=%d", Thread.currentThread().getName(),
                    System.currentTimeMillis() - start);
            return new SequenceResult(message);
        }
    }

    private static class ParallelGenericAction implements GenericAction {
        @Override
        public GenericActionResult exec() {
            long start = System.currentTimeMillis();
            try {
                Thread.sleep(new Random().nextLong(1000, 10000));
            } catch (InterruptedException e) {
                //
            }
            String message = String.format("Parallel end thread='%s' timeMs=%d", Thread.currentThread().getName(),
                    System.currentTimeMillis() - start);
            return new ParallelResult(message);
        }
    }

    private record SequenceResult(String result) implements GenericActionResult{}
    private record ParallelResult(String result) implements GenericActionResult{}
    private static class SharedState implements FlowState {
        private int counter;

        public SharedState(int counter) {
            this.counter = counter;
        }

        public int getCounter() {
            return counter;
        }

        public void increment() {
            this.counter++;
        }
    }
}
