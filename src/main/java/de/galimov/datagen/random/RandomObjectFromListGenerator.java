package de.galimov.datagen.random;

import java.util.List;

public class RandomObjectFromListGenerator<T> extends AbstractRngDataGenerator<T> {
    private final List<T> source;

    public RandomObjectFromListGenerator(List<T> source) {
        this.source = source;
    }

    @Override
    protected T generateInternal() {
        return source.get(getRandom().nextInt(source.size()));
    }
}
