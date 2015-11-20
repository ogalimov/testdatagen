package de.galimov.datagen.basic;

import de.galimov.datagen.api.DataGenerator;
import de.galimov.datagen.api.GenerationStep;
import de.galimov.datagen.recording.OngoingRecordingHolder;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractDataGenerator<T> implements DataGenerator<T> {
    private T value;
    private boolean attached = false;

    private final List<DataGenerator<?>> childGenerators = new LinkedList<DataGenerator<?>>();
    private final List<GenerationStep<T>> generationSteps = new LinkedList<GenerationStep<T>>();

    private Class<?> generatedClass = Object.class;

    @Override
    public void newGenerationCycle(Set<DataGenerator<?>> generators) {
        if(generators.contains(this)) {
            return;
        }

        value = null;
        attached = false;
        generators.add(this);

        for (DataGenerator<?> childGenerator : childGenerators) {
            childGenerator.newGenerationCycle(generators);
        }

        for (GenerationStep<T> generationStep : generationSteps) {
            generationStep.newGenerationCycle(generators);
        }
    }

    @Override
    public T getValue() {
        if (!attached) {
            OngoingRecordingHolder.endRecordingForGeneratorIfItIsCurrent(this);
            value = generateInternal();
            for (GenerationStep<T> generationStep : generationSteps) {
                value = generationStep.apply(value);
            }
            attached = true;
        }

        return value;
    }

    @Override
    public T generate() {
        newGenerationCycle(new HashSet<DataGenerator<?>>());
        return getValue();
    }

    @Override
    public Class<?> getGeneratedClass() {
        return generatedClass;
    }

    @Override
    public void setGeneratedClass(Class<?> generatedClass) {
        this.generatedClass = generatedClass;
    }

    @Override
    public void setSeed(long seed) {
        Random random = new Random(seed);
        for (DataGenerator<?> childGenerator : childGenerators) {
            childGenerator.setSeed(random.nextInt());
        }
    }

    public void registerChildGenerator(DataGenerator<?> dataGenerator) {
        childGenerators.add(dataGenerator);
    }

    @Override
    public void add(GenerationStep<T> generationStep) {
        generationSteps.add(generationStep);
    }

    @Override
    public void addStepF(Function<T, T> generationFunction) {
        add(new GenerationStep<T>() {
            @Override
            public T apply(T object) {
                return generationFunction.apply(object);
            }

            @Override
            public void newGenerationCycle(Set<DataGenerator<?>> generators) {
            }
        });
    }

    @Override
    public void addStepC(Consumer<T> generationFunction) {
        addStepF((t -> {
            generationFunction.accept(t);
            return t;
        }));
    }

    protected abstract T generateInternal();
}
