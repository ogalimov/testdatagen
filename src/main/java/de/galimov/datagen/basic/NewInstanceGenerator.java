package de.galimov.datagen.basic;

/**
 * A Generator that generates new Objects, using a no argument constructor.
 * <br>
 * Will throw a RuntimeException if the Class doesn't have such a constructor.
 */
public class NewInstanceGenerator<T> extends AbstractDataGenerator<T> {
    public NewInstanceGenerator(Class<? extends T> clazz) {
        super();
        setGeneratedClass(clazz);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected T generateInternal() {
        T generatedObject;
        try {
            generatedObject = (T) getGeneratedClass().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Class generated by " + this.getClass().getSimpleName() + " must have a public no argument constructor", e);
        }
        return generatedObject;
    }

    @Override
    public int getSize() {
        return 1;
    }
}
