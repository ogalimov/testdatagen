package de.galimov.datagen.basic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.galimov.datagen.api.DataGenerator;

public class ConstructorGeneratorTest {
    public static class TestClass {
        private final int x;
        private final long y;

        public TestClass(int x) {
            this.x = x;
            y = 0;
        }

        public TestClass(int x, int y) {
            this.x = x;
            this.y = y + 100;
        }

        public TestClass(int x, long y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestClass testClass = (TestClass) o;

            if (x != testClass.x) return false;
            if (y != testClass.y) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + (int) (y ^ (y >>> 32));
            return result;
        }
    }

    @Test
    public void testConstructorGenerationByTypes() {
        DataGenerator<Integer> generator1 = new ConstantGenerator<Integer>(int.class, 3);
        DataGenerator<Long> generator2 = new ConstantGenerator<Long>(long.class, 3L);
        ConstructorGenerator<TestClass> constructorGenerator = new ConstructorGenerator<TestClass>(TestClass.class, generator1, generator2);
        TestClass result = constructorGenerator.generate();
        assertEquals(new TestClass(3, 3L), result);
    }

    @Test
    public void testConstructorGenerationByNumberOfArguments() {
        DataGenerator<Integer> generator1 = new ConstantGenerator<Integer>(Integer.class, 3);
        ConstructorGenerator<TestClass> constructorGenerator = new ConstructorGenerator<TestClass>(TestClass.class, generator1);
        TestClass result = constructorGenerator.generate();
        assertEquals(new TestClass(3), result);
    }
}
