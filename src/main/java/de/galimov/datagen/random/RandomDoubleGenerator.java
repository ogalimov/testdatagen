package de.galimov.datagen.random;

public class RandomDoubleGenerator extends AbstractRngDataGenerator<Double> {
    public final int precision;
    public final int minExponent;
    public final int maxExponent;

    public RandomDoubleGenerator(int precision, int minExponent, int maxExponent) {
        this.precision = precision;
        this.minExponent = minExponent;
        this.maxExponent = maxExponent;
    }

    @Override
    protected Double generateInternal() {
        double randomDouble = getRandom().nextDouble();
        double roundedDouble = Math.round(randomDouble * Math.pow(10.0, precision)) / Math.pow(10.0, precision);

        int exponent = getRandom().nextInt(maxExponent - minExponent) + minExponent;

        return roundedDouble * Math.pow(10.0, exponent);
    }
}
