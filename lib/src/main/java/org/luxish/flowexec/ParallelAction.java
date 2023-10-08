package org.luxish.flowexec;

import java.util.List;
import java.util.function.Consumer;

public record ParallelAction(List<GenericAction> genericActions, Consumer<GenericActionResult> handler) implements FlowAction{
}
