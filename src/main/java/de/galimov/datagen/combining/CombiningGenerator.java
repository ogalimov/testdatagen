package de.galimov.datagen.combining;

import de.galimov.datagen.api.DataGenerator;
import de.galimov.datagen.random.AbstractRngDataGenerator;

public class CombiningGenerator<T> extends AbstractRngDataGenerator<T> {
    private int generatorCounter = 0;
    private int generatedObjectCounter = 0;

    private final DataGenerator[] generators;
    private Integer size = null;

    public CombiningGenerator(DataGenerator... generators) {
        this.generators = generators;

        for (DataGenerator generator : generators) {
            registerChildGenerator(generator);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected T generateInternal() {
        if (generators.length == 0) {
            return null;
        }

        if (generators[generatorCounter].getSize() == 0) {
            return generateRandom();
        }

        if (generators[generatorCounter].getSize() == generatedObjectCounter) {
            generatedObjectCounter = 0;
            generatorCounter++;

            if (generators[generatorCounter].getSize() == 0) {
                return generateRandom();
            }
        }

        generatedObjectCounter++;
        return (T) generators[generatorCounter].getValue();
    }

    @SuppressWarnings("unchecked")
    private T generateRandom() {
        int numGenerators = generators.length - generatorCounter;

        double probability = 1.0 / numGenerators;

        double randomDouble = getRandom().nextDouble();

        return (T) generators[Math.min(generators.length - 1, (int) Math.floor(randomDouble / probability))].getValue();
    }

    @Override
    public int getSize() {
        if (size == null) {
            int result = 0;

            for (DataGenerator generator : generators) {
                if (generator.getSize() == 0) {
                    result = 0;
                    break;
                }

                result += generator.getSize();
            }

            size = result;
        }

        return size;
    }
}
