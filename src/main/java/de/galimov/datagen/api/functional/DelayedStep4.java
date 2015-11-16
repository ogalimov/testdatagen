package de.galimov.datagen.api.functional;

public interface DelayedStep4<T1, T2, T3, T4> {
    void addStepC(Consumer4<T1, T2, T3, T4> consumer);
    void addStepF(Function4<T1, T2, T3, T4, T1> function);
}