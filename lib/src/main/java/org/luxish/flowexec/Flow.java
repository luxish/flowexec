package org.luxish.flowexec;

import java.util.ArrayList;
import java.util.List;

public class Flow<STATE extends FlowState> {

    private List<FlowAction> actions;

    private STATE sharedState;

    private Flow() {

    }
    public static FlowBuilder<FlowState.NoState> create() {
        Flow<FlowState.NoState> main = new Flow<>();
        return new FlowBuilder<>(main);
    }

    public static <T extends FlowState> FlowBuilder<T> create(T state) {
        Flow<T> main = new Flow<>();
        main.sharedState = state;
        return new FlowBuilder<>(main);
    }

    public void execute() {
        if(actions == null) {
            return;
        }
        actions.forEach(flowAction -> {
            if (flowAction instanceof SequenceAction) {
                SequenceAction<STATE> seqActions = (SequenceAction<STATE>) flowAction;
                seqActions.genericActions().stream()
                        .map(GenericAction::exec)
                        .forEach(result -> seqActions.handler().accept(result, sharedState));
            } else if (flowAction instanceof ParallelAction) {
                ParallelAction<STATE> parallelAction = (ParallelAction<STATE>) flowAction;
                parallelAction.genericActions().stream()
                        .parallel()
                        .map(GenericAction::exec)
                        .forEachOrdered(result -> parallelAction.handler().accept(result, sharedState));
            }
        });
    }

    public static class FlowBuilder<STATE extends FlowState> {
        private final Flow<STATE> flow;

        private final List<FlowAction> actions = new ArrayList<>();

        FlowBuilder(Flow<STATE> flow) {
            this.flow = flow;
        }

        public Sequence<STATE> sequence() {
            return new Sequence<>(this);
        }

        public Parallel<STATE> parallel() {
            return new Parallel<>(this);
        }

        protected void add(SequenceAction<STATE> sequenceAction) {
            actions.add(sequenceAction);
        }

        protected void add(ParallelAction<STATE> parallelAction) {
            actions.add(parallelAction);
        }

        public Flow<?> stop() {
            flow.actions = new ArrayList<>(actions);
            return flow;
        }

    }
}
