package org.luxish.flowexec;

import java.util.ArrayList;
import java.util.List;

public class Flow {

    private List<FlowAction> actions;

    public Flow() {

    }
    public FlowBuilder create() {
        return new FlowBuilder(this);
    }

    public void execute() {
        if(actions == null) {
            return;
        }
        actions.forEach(flowAction -> {
            if (flowAction instanceof SequenceAction seqActions) {
                seqActions.genericActions().stream()
                        .map(GenericAction::exec)
                        .forEach(seqActions.handler());
            } else if (flowAction instanceof ParallelAction parallelAction) {
                parallelAction.genericActions().stream()
                        .parallel()
                        .map(GenericAction::exec)
                        .forEachOrdered(parallelAction.handler());
            }
        });
    }

    public static class FlowBuilder {
        private final Flow flow;

        private final List<FlowAction> actions = new ArrayList<>();

        FlowBuilder(Flow flow) {
            this.flow = flow;
        }

        public Sequence sequence() {
            return new Sequence(this);
        }

        public Parallel parallel() {
            return new Parallel(this);
        }

        protected void add(SequenceAction sequenceAction) {
            actions.add(sequenceAction);
        }

        protected void add(ParallelAction parallelAction) {
            actions.add(parallelAction);
        }

        public Flow stop() {
            flow.actions = new ArrayList<>(actions);
            return flow;
        }

    }
}
