package de.galimov.datagen.recording;

import de.galimov.datagen.api.DataGenerator;

public class RecordingHelperUtils {
    @SuppressWarnings("unchecked")
    public static <T> T generated(DataGenerator<T> generator) {
        OngoingRecordingHolder.endRecordingForGeneratorIfItIsCurrent(generator);

        OngoingRecordingHolder.addParameter(generator);
        if (generator.getGeneratedClass().equals(Integer.class) || generator.getGeneratedClass().equals(int.class)) {
            return (T) (Integer) 0;
        }
        if (generator.getGeneratedClass().equals(Long.class) || generator.getGeneratedClass().equals(long.class)) {
            return (T) (Long) 0L;
        }
        if (generator.getGeneratedClass().equals(Boolean.class) || generator.getGeneratedClass().equals(boolean.class)) {
            return (T) (Boolean) false;
        }
        if (generator.getGeneratedClass().equals(Double.class) || generator.getGeneratedClass().equals(double.class)) {
            return (T) (Double) 0.0d;
        }
        if (generator.getGeneratedClass().equals(Float.class) || generator.getGeneratedClass().equals(float.class)) {
            return (T) (Float) 0.0f;
        }
        if (generator.getGeneratedClass().equals(Short.class) || generator.getGeneratedClass().equals(short.class)) {
            return (T) (Short) (short) 0;
        }
        if (generator.getGeneratedClass().equals(Byte.class) || generator.getGeneratedClass().equals(byte.class)) {
            return (T) (Byte) (byte) 0;
        }
        if (generator.getGeneratedClass().equals(Character.class) || generator.getGeneratedClass().equals(char.class)) {
            return (T) (Character) (char) 0;
        }
        return null;
    }
}