package org.luxish.flowexec;

public class Sequence<STATE extends FlowState> extends FlowActionCollector<STATE> {

    public Sequence(Flow.FlowBuilder<STATE> flow) {
        super(flow);
    }

    public Flow.FlowBuilder<STATE> then() {
        flowBuilder.add(new SequenceAction<>(actions, handler != null ? handler : (ac, state)->{}));
        return flowBuilder;
    }
}
