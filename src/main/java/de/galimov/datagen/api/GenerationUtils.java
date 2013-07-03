package de.galimov.datagen.api;

import java.util.ArrayList;

public class GenerationUtils {
    public static <T> ArrayList<T> listOfGeneratedObjects(DataGenerator<T> dataGenerator, int n) {
        ArrayList<T> result = new ArrayList<T>(n);
        for(int i = 0; i < n; i++) {
            result.add(dataGenerator.generate());
        }
        return result;
    }
}
