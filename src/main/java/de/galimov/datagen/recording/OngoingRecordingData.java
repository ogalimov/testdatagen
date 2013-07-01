package de.galimov.datagen.recording;

import de.galimov.datagen.api.DataGenerator;

public class OngoingRecordingData {
    private Invocation invocation = new Invocation();
    private InvocationChain invocationChain = new InvocationChain();

    private final DataGenerator generator;

    public OngoingRecordingData(DataGenerator generator) {
        this.generator = generator;
    }

    public void resetCurrentMethodInvocation() {
        invocation = new Invocation();
    }

    public void resetCurrentInvocationChain() {
        invocationChain = new InvocationChain();
    }

    public Invocation getInvocation() {
        return invocation;
    }

    public InvocationChain getInvocationChain() {
        return invocationChain;
    }

    public DataGenerator getGenerator() {
        return generator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OngoingRecordingData that = (OngoingRecordingData) o;

        return !(generator != null ? !generator.equals(that.generator) : that.generator != null);

    }

    @Override
    public int hashCode() {
        return generator != null ? generator.hashCode() : 0;
    }
}
