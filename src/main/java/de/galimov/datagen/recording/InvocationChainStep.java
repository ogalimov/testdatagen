package de.galimov.datagen.recording;

import java.util.Set;

import de.galimov.datagen.api.DataGenerator;
import de.galimov.datagen.api.GenerationStep;

public class InvocationChainStep<T> implements GenerationStep<T> {
    private final InvocationChain invocationChain;

    public InvocationChainStep(InvocationChain invocationChain) {
        this.invocationChain = invocationChain;
    }

    @Override
    public T apply(T object) {
        invocationChain.execute(object);
        return object;
    }

    @Override
    public void newGenerationCycle(Set<DataGenerator<?>> generators) {
        for (DataGenerator<?> dataGenerator : invocationChain.getGenerators()) {
            dataGenerator.newGenerationCycle(generators);
        }
    }
}
