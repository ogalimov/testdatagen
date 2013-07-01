package de.galimov.datagen.recording;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.galimov.datagen.api.DataGenerator;

public class InvocationChain {
    private final List<Invocation> invocations = new LinkedList<Invocation>();

    public void add(Invocation methodInvocation) {
        invocations.add(methodInvocation);
    }

    public void execute(Object obj) {
        Object currentObject = obj;
        for (Invocation methodInvocation : invocations) {
            currentObject = methodInvocation.execute(currentObject);
        }
    }

    public boolean hasInvocations() {
        return !invocations.isEmpty();
    }

    public Set<DataGenerator<?>> getGenerators() {
        Set<DataGenerator<?>> result = new HashSet<DataGenerator<?>>();

        for (Invocation methodInvocation : invocations) {
            result.addAll(methodInvocation.getParamGenerators());
        }

        return result;
    }
}
