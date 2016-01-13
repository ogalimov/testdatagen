package de.galimov.datagen.random;

import de.galimov.datagen.api.DataGenerator;

import java.util.Collection;
import java.util.Collections;

import static de.galimov.datagen.api.Generation.constant;

public class RandomAsciiStringGenerator extends AbstractRngDataGenerator<String> {
    private final DataGenerator<Integer> minNumChars;
    private final DataGenerator<Integer> maxNumChars;

    private final Collection<Character> forbiddenLetters;

    private static final int MIN_LETTER = 32;
    private static final int MAX_LETTER = 127;

    /**
     * @param minNumChars minimum Length of the generated String
     * @param maxNumChars maximum Length of the generated String
     */
    public RandomAsciiStringGenerator(DataGenerator<Integer> minNumChars, DataGenerator<Integer> maxNumChars) {
        this(minNumChars, maxNumChars, Collections.emptyList());
    }

    public RandomAsciiStringGenerator(Integer minNumChars, Integer maxNumChars) {
        this(constant(minNumChars), constant(maxNumChars));
    }

    public RandomAsciiStringGenerator(DataGenerator<Integer> minNumChars, DataGenerator<Integer> maxNumChars, Collection<Character> forbiddenLetters) {
        this.minNumChars = minNumChars;
        this.maxNumChars = maxNumChars;

        this.forbiddenLetters = forbiddenLetters;

        registerChildGenerator(minNumChars);
        registerChildGenerator(maxNumChars);

        setGeneratedClass(Integer.class);
    }

    public RandomAsciiStringGenerator(Integer minNumChars, Integer maxNumChars, Collection<Character> forbiddenLetters) {
        this(constant(minNumChars), constant(maxNumChars), forbiddenLetters);
    }

    @Override
    protected String generateInternal() {
        int length;
        if(maxNumChars.getValue() - minNumChars.getValue() > 0) {
            length = getRandom().nextInt(maxNumChars.getValue() - minNumChars.getValue()) + minNumChars.getValue();
        } else {
            length = minNumChars.getValue();
        }
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char randomChar;
            do {
                randomChar = (char) (getRandom().nextInt(MAX_LETTER - MIN_LETTER) + MIN_LETTER);
            } while (forbiddenLetters.contains(randomChar));
            result.append(randomChar);
        }

        return result.toString();
    }
}
