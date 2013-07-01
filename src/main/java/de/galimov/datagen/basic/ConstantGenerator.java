package de.galimov.datagen.basic;

public class ConstantGenerator<T> extends AbstractDataGenerator<T> {
    final T value;

    public ConstantGenerator(Class<?> generatedClass, T value) {
        this.value = value;
        setGeneratedClass(generatedClass);
    }

    public ConstantGenerator(T value) {
        this.value = value;
    }

    @Override
    protected T generateInternal() {
        return value;
    }

    @Override
    public int getSize() {
        return 1;
    }
}
