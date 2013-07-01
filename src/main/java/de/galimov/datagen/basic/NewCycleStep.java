package de.galimov.datagen.basic;

import java.util.HashSet;

import de.galimov.datagen.api.DataGenerator;

public class NewCycleStep<T> extends AbstractGenerationStep<T> {
    private final DataGenerator<?> generator;

    public NewCycleStep(DataGenerator<?> generator) {
        this.generator = generator;
    }

    @Override
    public T apply(T object) {
        generator.newGenerationCycle(new HashSet<DataGenerator<?>>());
        return object;
    }

}
