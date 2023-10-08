package org.luxish.flowexec;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class FlowActionCollector {
    protected final Flow.FlowBuilder flowBuilder;

    protected final List<GenericAction> actions = new ArrayList<>();

    protected Consumer<GenericActionResult> handler;

    public FlowActionCollector(Flow.FlowBuilder flow) {
        this.flowBuilder = flow;
    }

    public FlowActionCollector add(GenericAction action) {
        this.actions.add(action);
        return this;
    }

    public FlowActionCollector handler(Consumer<GenericActionResult> handler) {
        this.handler = handler;
        return this;
    }

    public abstract Flow.FlowBuilder then();
}
