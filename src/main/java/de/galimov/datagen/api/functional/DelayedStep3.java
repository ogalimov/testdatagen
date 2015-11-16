package de.galimov.datagen.api.functional;

public interface DelayedStep3<T1, T2, T3> {
    void addStepC(Consumer3<T1, T2, T3> consumer);
    void addStepF(Function3<T1, T2, T3, T1> function);
}