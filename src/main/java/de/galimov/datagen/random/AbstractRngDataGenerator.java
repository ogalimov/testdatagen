package de.galimov.datagen.random;

import java.util.Random;

import de.galimov.datagen.basic.AbstractDataGenerator;

public abstract class AbstractRngDataGenerator<T> extends AbstractDataGenerator<T> {
    private Random random = new Random();

    @Override
    public void setSeed(long seed) {
        random.setSeed(seed);
    }

    @Override
    public int getSize() {
        return 0;
    }

    protected Random getRandom() {
        return random;
    }

    public void randomizeUsingTime() {
        setSeed(System.currentTimeMillis());
    }
}
