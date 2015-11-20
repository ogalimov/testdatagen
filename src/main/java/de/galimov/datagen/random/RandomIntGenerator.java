package de.galimov.datagen.random;

import de.galimov.datagen.api.DataGenerator;

import static de.galimov.datagen.api.Generation.constant;

public class RandomIntGenerator extends AbstractRngDataGenerator<Integer> {
    private final DataGenerator<Integer> minValue;
    private final DataGenerator<Integer> maxValue;

    /**
     * @param minValue lower bound (inclusive); must be greater than Long.MIN_VALUE / 2
     * @param maxValue upper bound (exclusive); must be less then Long.MAX_VALUE / 2
     */
    public RandomIntGenerator(DataGenerator<Integer> minValue, DataGenerator<Integer> maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;

        registerChildGenerator(minValue);
        registerChildGenerator(maxValue);

        setGeneratedClass(Integer.class);
    }

    public RandomIntGenerator(Integer minValue, Integer maxValue) {
        this(constant(minValue), constant(maxValue));
    }

    @Override
    protected Integer generateInternal() {
        return getRandom().nextInt(maxValue.getValue() - minValue.getValue()) + minValue.getValue();
    }
}
