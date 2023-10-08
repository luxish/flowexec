package org.luxish.flowexec;

public class Parallel extends FlowActionCollector {
    public Parallel(Flow.FlowBuilder flow) {
        super(flow);
    }

    @Override
    public Flow.FlowBuilder then() {
        flowBuilder.add(new ParallelAction(actions, handler != null ? handler : (ac)->{}));
        return flowBuilder;
    }
}
