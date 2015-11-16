package de.galimov.datagen.api;

import de.galimov.datagen.api.functional.*;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class BindGeneration {
    public static <T, P1> DelayedStep2<T, P1> bind(DataGenerator<T> target, DataGenerator<P1> param1) {
        target.registerChildGenerator(param1);
        return new DelayedStep2<T, P1>() {
            @Override
            public void addStepC(BiConsumer<T, P1> consumer) {
                addStepF(((t, p1) -> {
                    consumer.accept(t, p1);
                    return t;
                }));
            }

            @Override
            public void addStepF(BiFunction<T, P1, T> function) {
                target.addStepF(t -> function.apply(t, param1.getValue()));
            }
        };
    }

    public static <T, P1, P2> DelayedStep3<T, P1, P2> bind(DataGenerator<T> target, DataGenerator<P1> param1, DataGenerator<P2> param2) {
        target.registerChildGenerator(param1);
        return new DelayedStep3<T, P1, P2>() {
            @Override
            public void addStepC(Consumer3<T, P1, P2> consumer) {
                addStepF(((t, p1, p2) -> {
                    consumer.accept(t, p1, p2);
                    return t;
                }));
            }

            @Override
            public void addStepF(Function3<T, P1, P2, T> function) {
                target.addStepF(t -> function.apply(t, param1.getValue(), param2.getValue()));
            }
        };
    }

    public static <T, P1, P2, P3> DelayedStep4<T, P1, P2, P3> bind(DataGenerator<T> target, DataGenerator<P1> param1, DataGenerator<P2> param2, DataGenerator<P3> param3) {
        target.registerChildGenerator(param1);
        return new DelayedStep4<T, P1, P2, P3>() {
            @Override
            public void addStepC(Consumer4<T, P1, P2, P3> consumer) {
                addStepF(((t, p1, p2, p3) -> {
                    consumer.accept(t, p1, p2, p3);
                    return t;
                }));
            }

            @Override
            public void addStepF(Function4<T, P1, P2, P3, T> function) {
                target.addStepF(t -> function.apply(t, param1.getValue(), param2.getValue(), param3.getValue()));
            }
        };
    }
}
