package de.galimov.datagen;

import de.galimov.datagen.api.DataGenerator;
import de.galimov.datagen.basic.AbstractGenerationStep;
import de.galimov.datagen.basic.NewCycleStep;
import de.galimov.datagen.basic.NewInstanceGenerator;
import de.galimov.datagen.random.RandomLongGenerator;
import de.galimov.datagen.serial.SerialLongGenerator;
import org.junit.Test;

import static de.galimov.datagen.api.Generation.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GeneratorTest {
    public static class TestClass {
        int x;
        long y;
        long z;
        TestClass t;
        TestClass t2;

        public TestClass() {
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public long getY() {
            return y;
        }

        public void setY(long y) {
            this.y = y;
        }

        public long getZ() {
            return z;
        }

        public void setZ(long z) {
            this.z = z;
        }

        public long setAndReturnY(long y) {
            this.y = y;
            return y;
        }

        public void setCoordinates(int x, long y) {
            this.x = x;
            this.y = y;
        }

        public TestClass getT() {
            return t;
        }

        public void setT(TestClass t) {
            this.t = t;
        }

        public void setTAndT2(TestClass t, TestClass t2) {
            this.t = t;
            this.t2 = t2;
        }

        public TestClass getT2() {
            return t2;
        }

        public TestClass setAndReturnT(TestClass t) {
            this.t = t;
            return t;
        }
    }

    @Test
    public void testSingleArgumentManual() {
        DataGenerator<TestClass> generator = new NewInstanceGenerator<>(TestClass.class);

        generator.add(new AbstractGenerationStep<TestClass>() {
            @Override
            public TestClass apply(TestClass object) {
                object.setX(3);
                object.setY(5);
                return object;
            }
        });

        TestClass generatedObject = generator.generate();
        assertEquals(3, generatedObject.getX());
        assertEquals(5, generatedObject.getY());
    }

    @Test
    public void testSingleArgument() {
        DataGenerator<TestClass> generator = new NewInstanceGenerator<>(TestClass.class);

        on(generator).setX(value(3));
        on(generator).setY(value(5L));

        TestClass generatedObject = generator.generate();
        assertEquals(3, generatedObject.getX());
        assertEquals(5, generatedObject.getY());
    }

    @Test
    public void testMultiArgument() {
        DataGenerator<TestClass> generator = new NewInstanceGenerator<>(TestClass.class);

        on(generator).setCoordinates(value(3), value(5L));

        TestClass generatedObject = generator.generate();
        assertEquals(3, generatedObject.getX());
        assertEquals(5, generatedObject.getY());
    }

    @Test
    public void testChaining() {
        DataGenerator<TestClass> generator = new NewInstanceGenerator<>(TestClass.class);

        on(generator).setT(value(new TestClass()));
        on(generator).getT().setX(value(3));
        on(generator).getT().setY(generated(new RandomLongGenerator(1L, 5L)));

        TestClass generatedObject = generator.generate();
        assertEquals(3, generatedObject.getT().getX());
        assertTrue(generatedObject.getT().getY() < 5 && generatedObject.getT().getY() >= 1);
    }

    @Test
    public void testInlineChaining() {
        DataGenerator<TestClass> generator = new NewInstanceGenerator<>(TestClass.class);

        on(generator).setT(value(new TestClass()));
        on(generator).getT().setT(generated(randomInlineTGenerator(3, false)));

        TestClass generatedObject = generator.generate();
        assertEquals(3, generatedObject.getT().getT().getX());
    }

    @Test
    public void testRecursiveInlineChaining() {
        DataGenerator<TestClass> generator = new NewInstanceGenerator<>(TestClass.class);

        on(generator).setT(value(new TestClass()));
        on(generator).getT().setT(generated(randomInlineTGenerator(3, true)));

        TestClass generatedObject = generator.generate();
        assertEquals(3, generatedObject.getT().getT().getX());
        assertEquals(3, generatedObject.getT().getT().getT().getX());
    }

    @Test
    public void testDoubleRecursiveInlineChaining() {
        DataGenerator<TestClass> generator = new NewInstanceGenerator<>(TestClass.class);

        on(generator).setT(value(new TestClass()));
        on(generator).getT().setTAndT2(generated(randomInlineTGenerator(3, true)), generated(randomInlineTGenerator(2, true)));

        TestClass generatedObject = generator.generate();
        assertEquals(3, generatedObject.getT().getT().getX());
        assertEquals(3, generatedObject.getT().getT().getT().getX());
        assertEquals(2, generatedObject.getT().getT2().getT().getX());
    }

    private DataGenerator<TestClass> randomInlineTGenerator(int xValue, boolean recursive) {
        DataGenerator<TestClass> generator = new NewInstanceGenerator<>(TestClass.class);
        on(generator).setX(value(xValue));
        if (recursive) {
            on(generator).setAndReturnT(generated(randomInlineTGenerator(xValue, false)));
        }
        return generator;
    }

    @Test
    public void testRecursiveChaining() {
        DataGenerator<TestClass> generator1 = new NewInstanceGenerator<>(TestClass.class);
        DataGenerator<TestClass> generator2 = new NewInstanceGenerator<>(TestClass.class);

        on(generator1).setX(value(3));
        on(generator1).setY(generated(new RandomLongGenerator(1L, 5L)));

        on(generator2).setT(generated(generator1));

        TestClass generatedObject = generator2.generate();
        assertEquals(3, generatedObject.getT().getX());
        assertTrue(generatedObject.getT().getY() < 5 && generatedObject.getT().getY() >= 1);
    }

    @Test
    public void testNonVoidMethod() {
        DataGenerator<TestClass> generator1 = new NewInstanceGenerator<>(TestClass.class);
        DataGenerator<TestClass> generator2 = new NewInstanceGenerator<>(TestClass.class);

        on(generator1).setX(value(3));
        on(generator1).setAndReturnY(generated(new RandomLongGenerator(1L, 5L)));

        on(generator2).setAndReturnT(generated(generator1));

        TestClass generatedObject = generator2.generate();
        assertEquals(3, generatedObject.getT().getX());
        assertTrue(generatedObject.getT().getY() < 5 && generatedObject.getT().getY() >= 1);
    }

    @Test
    public void testSameGenerator() {
        DataGenerator<TestClass> generator = new NewInstanceGenerator<>(TestClass.class);
        DataGenerator<Long> serialLongGenerator = new SerialLongGenerator(3L);
        on(generator).setY(generated(serialLongGenerator));
        on(generator).setZ(generated(serialLongGenerator));

        TestClass generatedObject = generator.generate();
        assertEquals(3, generatedObject.getY());
        assertEquals(3, generatedObject.getZ());
    }

    @Test
    public void testSameGenerator_decoupledOnTargetGenerator() {
        DataGenerator<TestClass> generator = new NewInstanceGenerator<>(TestClass.class);
        DataGenerator<Long> serialLongGenerator = new SerialLongGenerator(3L);
        on(generator).setY(generated(serialLongGenerator));
        generator.add(new NewCycleStep<>(generator));
        on(generator).setZ(generated(serialLongGenerator));

        TestClass generatedObject = generator.generate();
        assertEquals(3, generatedObject.getY());
        assertEquals(4, generatedObject.getZ());
    }

    @Test
    public void testSameGenerator_decoupledOnSourceGenerator() {
        DataGenerator<TestClass> generator = new NewInstanceGenerator<>(TestClass.class);
        DataGenerator<Long> serialLongGenerator = new SerialLongGenerator(3L);
        on(generator).setY(generated(serialLongGenerator));
        generator.add(new NewCycleStep<>(serialLongGenerator));
        on(generator).setZ(generated(serialLongGenerator));

        TestClass generatedObject = generator.generate();
        assertEquals(3, generatedObject.getY());
        assertEquals(4, generatedObject.getZ());
    }
}
