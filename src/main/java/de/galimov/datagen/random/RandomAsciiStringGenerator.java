package de.galimov.datagen.random;

import de.galimov.datagen.api.DataGenerator;
import de.galimov.datagen.random.AbstractRngDataGenerator;

import static de.galimov.datagen.api.Generation.constant;

public class RandomAsciiStringGenerator extends AbstractRngDataGenerator<String> {
    private final DataGenerator<Integer> minNumChars;
    private final DataGenerator<Integer> maxNumChars;

    private static final int MIN_LETTER = 32;
    private static final int MAX_LETTER = 127;

    /**
     * @param minNumChars minimum Length of the generated String
     * @param maxNumChars maximum Length of the generated String
     */
    public RandomAsciiStringGenerator(DataGenerator<Integer> minNumChars, DataGenerator<Integer> maxNumChars) {
        this.minNumChars = minNumChars;
        this.maxNumChars = maxNumChars;

        registerChildGenerator(minNumChars);
        registerChildGenerator(maxNumChars);

        setGeneratedClass(Integer.class);
    }

    public RandomAsciiStringGenerator(Integer minNumChars, Integer maxNumChars) {
        this(constant(minNumChars), constant(maxNumChars));
    }

    @Override
    protected String generateInternal() {
        int length = getRandom().nextInt(maxNumChars.getValue() - minNumChars.getValue()) + minNumChars.getValue();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            result.append(((char) (getRandom().nextInt(MAX_LETTER - MIN_LETTER) + MIN_LETTER)));
        }

        return result.toString();
    }
}
