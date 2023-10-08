package org.luxish.flowexec;

import java.util.List;
import java.util.function.BiConsumer;

public record SequenceAction<STATE extends FlowState>(List<GenericAction> genericActions, BiConsumer<GenericActionResult, STATE> handler) implements FlowAction<STATE>{
}
