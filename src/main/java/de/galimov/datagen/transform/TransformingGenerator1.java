package de.galimov.datagen.transform;

import de.galimov.datagen.api.DataGenerator;
import de.galimov.datagen.basic.AbstractDataGenerator;

import java.util.function.Function;

public class TransformingGenerator1<T, R> extends AbstractDataGenerator<R> {
    private final DataGenerator<T> arg;
    private final Function<T, R> func;

    public TransformingGenerator1(DataGenerator<T> arg, Function<T, R> func) {
        this.arg = arg;
        this.func = func;
    }


    @Override
    protected R generateInternal() {
        return func.apply(arg.getValue());
    }

    @Override
    public int getSize() {
        return arg.getSize();
    }
}
