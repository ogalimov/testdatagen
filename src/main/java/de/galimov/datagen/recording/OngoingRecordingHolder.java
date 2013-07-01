package de.galimov.datagen.recording;

import java.lang.reflect.Method;
import java.util.Stack;

import de.galimov.datagen.api.DataGenerator;

public class OngoingRecordingHolder {
    private static final ThreadLocal<Stack<OngoingRecordingData>> RECORDING_STACK = new ThreadLocal<Stack<OngoingRecordingData>>() {
        @Override
        protected Stack<OngoingRecordingData> initialValue() {
            return new Stack<OngoingRecordingData>();
        }
    };

    public static void setCurrentMethod(Method method) {
        RECORDING_STACK.get().peek().getInvocation().setMethod(method);
    }

    public static void addParameter(DataGenerator<?> dataGenerator) {
        RECORDING_STACK.get().peek().getInvocation().addParamGenerator(dataGenerator);
    }

    public static void setCurrentGenerator(DataGenerator generator) {
        if (RECORDING_STACK.get().contains(new OngoingRecordingData(generator))) {
            RECORDING_STACK.get().remove(new OngoingRecordingData(generator));
        }
        RECORDING_STACK.get().push(new OngoingRecordingData(generator));
    }

    public static DataGenerator getCurrentGenerator() {
        if (RECORDING_STACK.get().empty()) {
            RECORDING_STACK.remove();
            return null;
        }

        return RECORDING_STACK.get().peek().getGenerator();
    }

    public static void removeGenerator(DataGenerator builder) {
        RECORDING_STACK.get().remove(new OngoingRecordingData(builder));
        if (RECORDING_STACK.get().empty()) {
            RECORDING_STACK.remove();
        }
    }

    public static void endRecordingForGeneratorIfItIsCurrent(DataGenerator<?> generator) {
        if (getCurrentGenerator() == generator) {
            storeInvocation();
            storeInvocationChain();
            removeGenerator(generator);
        }
    }

    public static void storeInvocation() {
        if (RECORDING_STACK.get().empty()) {
            RECORDING_STACK.remove();
            return;
        }

        OngoingRecordingData currentOngoingRecordingData = RECORDING_STACK.get().peek();
        if (currentOngoingRecordingData.getInvocation().hasMethod()) {
            currentOngoingRecordingData.getInvocationChain().add(currentOngoingRecordingData.getInvocation());

            currentOngoingRecordingData.resetCurrentMethodInvocation();
        }
    }

    @SuppressWarnings("unchecked")
    public static void storeInvocationChain() {
        if (RECORDING_STACK.get().empty()) {
            RECORDING_STACK.remove();
            return;
        }

        OngoingRecordingData currentMethodInvocationData = RECORDING_STACK.get().peek();
        if (currentMethodInvocationData.getInvocationChain().hasInvocations()) {
            getCurrentGenerator().add(new InvocationChainStep(currentMethodInvocationData.getInvocationChain()));
            currentMethodInvocationData.resetCurrentMethodInvocation();
            currentMethodInvocationData.resetCurrentInvocationChain();
        }
    }
}
