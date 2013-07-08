package de.galimov.datagen.api;

import java.io.Serializable;
import java.util.Set;

public interface DataGenerator<T> extends Serializable {
    void newGenerationCycle(Set<DataGenerator<?>> generators);

    T getValue();

    T generate();

    void setSeed(long seed);

    Class<?> getGeneratedClass();

    int getSize();

    void setGeneratedClass(Class<?> generatedClass);

    void add(GenerationStep<T> generationStep);
}
