package de.galimov.datagen.recording;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import de.galimov.datagen.api.DataGenerator;

public class Invocation {
    private Method method;
    private final List<DataGenerator<?>> paramGenerators = new LinkedList<DataGenerator<?>>();

    public void setMethod(Method method) {
        this.method = method;
    }

    public void addParamGenerator(DataGenerator<?> dataGenerator) {
        paramGenerators.add(dataGenerator);
    }

    public boolean hasMethod() {
        return method != null;
    }

    public Object execute(Object obj) {
        LinkedList<Object> params = new LinkedList<Object>();
        for (DataGenerator<?> paramGenerator : paramGenerators) {
            params.add(paramGenerator.getValue());
        }
        try {
            return method.invoke(obj, params.toArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<DataGenerator<?>> getParamGenerators() {
        return paramGenerators;
    }
}
