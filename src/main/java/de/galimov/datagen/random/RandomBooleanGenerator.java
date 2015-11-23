package de.galimov.datagen.random;

public class RandomBooleanGenerator extends AbstractRngDataGenerator<Boolean> {
    public RandomBooleanGenerator() {
        setGeneratedClass(Boolean.class);
    }

    @Override
    protected Boolean generateInternal() {
        return getRandom().nextBoolean();
    }
}
