package org.luxish.flowexec;

import java.util.List;
import java.util.function.BiConsumer;

public record ParallelAction<STATE extends FlowState>(List<GenericAction> genericActions, BiConsumer<GenericActionResult, STATE> handler) implements FlowAction{
}
