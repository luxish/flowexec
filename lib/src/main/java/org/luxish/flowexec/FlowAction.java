package org.luxish.flowexec;

public sealed interface FlowAction<STATE extends FlowState> permits ParallelAction, SequenceAction {
}
