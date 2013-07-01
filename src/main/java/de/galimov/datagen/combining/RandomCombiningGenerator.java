package de.galimov.datagen.combining;

import java.util.Arrays;

import de.galimov.datagen.api.DataGenerator;
import de.galimov.datagen.random.AbstractRngDataGenerator;

public class RandomCombiningGenerator<T> extends AbstractRngDataGenerator<T> {
    private final double[] probabilities;
    private final DataGenerator[] generators;

    public RandomCombiningGenerator(DataGenerator... generators) {
        this(generateProbabilities(generators.length), generators);
    }

    public RandomCombiningGenerator(double[] probabilities, DataGenerator... generators) {
        if(probabilities.length != generators.length) {
            throw new IllegalArgumentException("Length of probabilities and generators must be the same");
        }

        this.probabilities = probabilities;
        this.generators = generators;

        for (DataGenerator generator : generators) {
            registerChildGenerator(generator);
        }
    }

    private static double[] generateProbabilities(int length) {
        double[] result = new double[length];
        Arrays.fill(result, 1.0 / length);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected T generateInternal() {
        if (generators.length == 0) {
            return null;
        }

        double randomDouble = getRandom().nextDouble();

        for (int i = 0; i < probabilities.length; i++) {
            randomDouble -= probabilities[i];
            if(randomDouble < 0) {
                return (T) generators[i].getValue();
            }
        }

        return (T) generators[generators.length - 1].getValue();
    }
}
