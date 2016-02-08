package de.galimov.datagen.random;

public class RandomEnumGenerator<T extends Enum<T>> extends AbstractRngDataGenerator<T> {
    
    private T[] enumConstants;

    public RandomEnumGenerator(Class<T> enumClass) {
        setGeneratedClass(enumClass);
        enumConstants = enumClass.getEnumConstants();
    }

    @Override
    protected T generateInternal() {
        return enumConstants[getRandom().nextInt(enumConstants.length)];
    }

}
