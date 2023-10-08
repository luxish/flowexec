package org.luxish.flowexec;

public class Sequence extends  FlowActionCollector {

    public Sequence(Flow.FlowBuilder flow) {
        super(flow);
    }

    public Flow.FlowBuilder then() {
        flowBuilder.add(new SequenceAction(actions, handler != null ? handler : (ac)->{}));
        return flowBuilder;
    }
}
