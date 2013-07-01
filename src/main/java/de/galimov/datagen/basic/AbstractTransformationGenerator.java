package de.galimov.datagen.basic;

import de.galimov.datagen.api.DataGenerator;

public abstract class AbstractTransformationGenerator<T, V> extends AbstractDataGenerator<V> {
    private final DataGenerator<? extends T> source;

    public AbstractTransformationGenerator(DataGenerator<? extends T> source) {
        this.source = source;

        registerChildGenerator(source);
    }

    @Override
    protected V generateInternal() {
        return transform(source.getValue());
    }

    @Override
    public int getSize() {
        return source.getSize();
    }

    protected abstract V transform(T value);
}
