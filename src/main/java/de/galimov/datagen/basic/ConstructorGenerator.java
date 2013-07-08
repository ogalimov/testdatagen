package de.galimov.datagen.basic;

import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;

import de.galimov.datagen.api.DataGenerator;

public class ConstructorGenerator<T> extends AbstractDataGenerator<T> {
    private final Constructor<T> constructor;
    private final DataGenerator[] parameterGenerators;

    public ConstructorGenerator(Class<T> clazz, DataGenerator... parameterGenerators) {
        this(findConstructor(clazz, parameterGenerators), parameterGenerators);
    }

    public ConstructorGenerator(Constructor<T> constructor, DataGenerator... parameterGenerators) {
        this.constructor = constructor;
        this.parameterGenerators = parameterGenerators;

        setGeneratedClass(constructor.getDeclaringClass());

        for (DataGenerator<?> parameterGenerator : parameterGenerators) {
            registerChildGenerator(parameterGenerator);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> Constructor<T> findConstructor(Class<T> clazz, DataGenerator[] parameterGenerators) {
        List<Constructor<?>> constructors = getConstructorsByNumberOfArguments(clazz, parameterGenerators.length);

        if (constructors.size() == 0) {
            throw new IllegalArgumentException("No constructor matching the number of parameters found");
        } else if (constructors.size() == 1) {
            return (Constructor<T>) constructors.get(0);
        } else {
            return findConstructorByClasses(clazz, parameterGenerators);
        }


    }

    private static <T> List<Constructor<?>> getConstructorsByNumberOfArguments(Class<T> clazz, int numberOfArguments) {
        Constructor<?>[] constructors = clazz.getConstructors();
        List<Constructor<?>> constructorsWithMatchingNumberOfArguments = new LinkedList<Constructor<?>>();
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterTypes().length == numberOfArguments) {
                    constructorsWithMatchingNumberOfArguments.add(constructor);
            }
        }
        return constructorsWithMatchingNumberOfArguments;
    }

    private static <T> Constructor<T> findConstructorByClasses(Class<T> clazz, DataGenerator[] parameterGenerators) {
        Class[] parameterClasses = new Class[parameterGenerators.length];

        for (int i = 0; i < parameterGenerators.length; i++) {
            parameterClasses[i] = parameterGenerators[i].getGeneratedClass();

            if (parameterClasses[i] == null) {
                throw new IllegalArgumentException("When using ConstructorGenerator by class, the generatedClass must be set " +
                        "for all parameter generators, if there is more than one constructor with the given number of arguments");
            }
        }

        try {
            return clazz.getConstructor(parameterClasses);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("No constructor matching the signature of the parameter generators found", e);
        }
    }

    @Override
    protected T generateInternal() {
        Object[] parameters = new Object[parameterGenerators.length];
        for (int i = 0; i < parameterGenerators.length; i++) {
            parameters[i] = parameterGenerators[i].getValue();
        }

        try {
            return constructor.newInstance(parameters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getSize() {
        return 0;
    }
}