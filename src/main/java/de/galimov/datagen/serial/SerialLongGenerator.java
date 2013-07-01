package de.galimov.datagen.serial;

import de.galimov.datagen.basic.AbstractDataGenerator;

public class SerialLongGenerator extends AbstractDataGenerator<Long> {
    private long value;

    public SerialLongGenerator(long startValue) {
        this.value = startValue;
        setGeneratedClass(Long.class);
    }

    @Override
    protected Long generateInternal() {
        return value++;
    }

    @Override
    public int getSize() {
        return 0;
    }
}
