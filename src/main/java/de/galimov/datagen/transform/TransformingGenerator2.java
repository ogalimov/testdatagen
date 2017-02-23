package de.galimov.datagen.transform;

import de.galimov.datagen.api.DataGenerator;
import de.galimov.datagen.basic.AbstractDataGenerator;

import java.util.function.BiFunction;

public class TransformingGenerator2<T1, T2, R> extends AbstractDataGenerator<R> {
    private final DataGenerator<T1> arg1;
    private final DataGenerator<T2> arg2;
    private final BiFunction<T1, T2, R> func;

    public TransformingGenerator2(DataGenerator<T1> arg1, DataGenerator<T2> arg2, BiFunction<T1, T2, R> func) {
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.func = func;
        this.registerChildGenerator(arg1);
        this.registerChildGenerator(arg2);
    }

    @Override
    protected R generateInternal() {
        return func.apply(arg1.getValue(), arg2.getValue());
    }

    @Override
    public int getSize() {
        return Math.min(arg1.getSize(), arg2.getSize());
    }
}
