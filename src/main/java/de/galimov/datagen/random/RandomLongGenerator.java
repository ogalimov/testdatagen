package de.galimov.datagen.random;

import java.util.Random;

import de.galimov.datagen.api.DataGenerator;

public class RandomLongGenerator extends AbstractRngDataGenerator<Long> {
    private final DataGenerator<Long> minValue;
    private final DataGenerator<Long> maxValue;

    /**
     * @param minValue lower bound (inclusive); must be greater than Long.MIN_VALUE / 2
     * @param maxValue upper bound (exclusive); must be less then Long.MAX_VALUE / 2
     */
    public RandomLongGenerator(DataGenerator<Long> minValue, DataGenerator<Long> maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;

        registerChildGenerator(minValue);
        registerChildGenerator(maxValue);

        setGeneratedClass(Long.class);
    }

    @Override
    protected Long generateInternal() {
        return nextLong(getRandom(), maxValue.getValue() - minValue.getValue()) + minValue.getValue();
    }

    /**
     * Helper-Method to generate a random Long
     *
     * @param rng the random number generator
     * @param n the upper bound of the generated value (exclusive)
     *
     * @return the random long
     */
    public static long nextLong(Random rng, long n) {
        if(n == 0) {
            return 0;
        }

        long bits, val;
        do {
            bits = (rng.nextLong() << 1) >>> 1;
            val = bits % n;
        } while (bits - val + (n - 1) < 0L);
        return val;
    }

}
