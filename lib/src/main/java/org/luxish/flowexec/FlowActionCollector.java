package org.luxish.flowexec;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public abstract class FlowActionCollector<STATE extends FlowState> {
    protected final Flow.FlowBuilder<STATE> flowBuilder;

    protected final List<GenericAction> actions = new ArrayList<>();

    protected BiConsumer<GenericActionResult, STATE> handler;

    public FlowActionCollector(Flow.FlowBuilder<STATE> flow) {
        this.flowBuilder = flow;
    }

    public FlowActionCollector<STATE> add(GenericAction action) {
        this.actions.add(action);
        return this;
    }

    public FlowActionCollector<STATE> handler(BiConsumer<GenericActionResult, STATE> handler) {
        this.handler = handler;
        return this;
    }

    public abstract Flow.FlowBuilder<STATE> then();
}
