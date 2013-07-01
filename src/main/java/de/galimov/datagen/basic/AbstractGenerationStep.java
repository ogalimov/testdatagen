package de.galimov.datagen.basic;

import java.util.Set;

import de.galimov.datagen.api.DataGenerator;
import de.galimov.datagen.api.GenerationStep;

public abstract class AbstractGenerationStep<T> implements GenerationStep<T> {
    @Override
    public void newGenerationCycle(Set<DataGenerator<?>> generators) {
    }
}
