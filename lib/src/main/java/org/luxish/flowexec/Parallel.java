package org.luxish.flowexec;

public class Parallel<STATE extends FlowState> extends FlowActionCollector<STATE> {
    public Parallel(Flow.FlowBuilder<STATE> flow) {
        super(flow);
    }

    @Override
    public Flow.FlowBuilder<STATE> then() {
        flowBuilder.add(new ParallelAction<>(actions, handler != null ? handler : (ac, state)->{}));
        return flowBuilder;
    }
}
