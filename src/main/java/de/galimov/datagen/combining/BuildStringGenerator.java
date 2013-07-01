package de.galimov.datagen.combining;

import de.galimov.datagen.api.DataGenerator;
import de.galimov.datagen.basic.AbstractDataGenerator;

public class BuildStringGenerator extends AbstractDataGenerator<String> {
    private final Object[] parts;

    public BuildStringGenerator(Object... parts) {
        this.parts = parts;

        for (Object part : parts) {
            if(part instanceof DataGenerator) {
                registerChildGenerator((DataGenerator<?>) part);
            }
        }

        setGeneratedClass(String.class);
    }

    @Override
    protected String generateInternal() {
        StringBuilder result = new StringBuilder();

        for (Object part : parts) {
            if(part instanceof DataGenerator) {
                result.append(((DataGenerator) part).getValue().toString());
            } else {
                result.append(part.toString());
            }
        }

        return result.toString();
    }


    @Override
    public int getSize() {
        return 0;
    }
}
