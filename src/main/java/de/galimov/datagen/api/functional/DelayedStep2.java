package de.galimov.datagen.api.functional;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public interface DelayedStep2<T1, T2> {
    void addStepC(BiConsumer<T1, T2> consumer);
    void addStepF(BiFunction<T1, T2, T1> function);
}