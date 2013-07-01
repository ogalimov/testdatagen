package de.galimov.datagen.api;

import java.util.Set;

public interface GenerationStep<T> {
    public T apply(T object);

    void newGenerationCycle(Set<DataGenerator<?>> generators);
}
