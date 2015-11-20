package de.galimov.datagen.random;

import de.galimov.datagen.basic.AbstractDataGenerator;

import java.util.Random;

public abstract class AbstractRngDataGenerator<T> extends AbstractDataGenerator<T> {
    private Random random = new Random(0);

    @Override
    public void setSeed(long seed) {
        super.setSeed(seed);
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
