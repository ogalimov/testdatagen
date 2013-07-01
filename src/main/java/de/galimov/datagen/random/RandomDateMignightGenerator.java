package de.galimov.datagen.random;

import org.joda.time.DateMidnight;
import org.joda.time.Days;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePeriod;

import de.galimov.datagen.api.DataGenerator;
import de.galimov.datagen.basic.AbstractTransformationGenerator;

public class RandomDateMignightGenerator extends AbstractRngDataGenerator<DateMidnight> {
    private final DataGenerator<? extends ReadableInstant> start;
    private final DataGenerator<? extends ReadableInstant> end;

    public RandomDateMignightGenerator(DataGenerator<? extends ReadableInstant> start, DataGenerator<? extends ReadableInstant> end) {
        this.start = start;
        this.end = end;

        registerChildGenerator(start);
        registerChildGenerator(end);

        setGeneratedClass(DateMidnight.class);
    }

    public RandomDateMignightGenerator(final DataGenerator<? extends ReadableInstant> start, final ReadablePeriod period) {
        this.start = start;
        end = new AbstractTransformationGenerator<ReadableInstant, ReadableInstant>(start) {
            @Override
            protected ReadableInstant transform(ReadableInstant value) {
                return new DateMidnight(start.getValue()).withPeriodAdded(period, 1);
            }
        };
    }


    @Override
    protected DateMidnight generateInternal() {
        int days = Days.daysBetween(new DateMidnight(start.getValue()), new DateMidnight(end.getValue())).getDays();

        return new DateMidnight(start.getValue()).plusDays(getRandom().nextInt(days));
    }
}
