package de.galimov.datagen.api;

import java.io.Serializable;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public interface DataGenerator<T> extends Serializable {
    void newGenerationCycle(Set<DataGenerator<?>> generators);

    T getValue();

    T generate();

    void setSeed(long seed);

    Class<?> getGeneratedClass();

    int getSize();

    void registerChildGenerator(DataGenerator<?> dataGenerator);

    void setGeneratedClass(Class<?> generatedClass);

    void add(GenerationStep<T> generationStep);

    void addStepF(Function<T, T> generationFunction);

    void addStepC(Consumer<T> generationFunction);
}
