package de.galimov.datagen.random;

import de.galimov.datagen.api.DataGenerator;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;

import static de.galimov.datagen.api.Generation.constant;

public class RandomLocalDateGenerator extends AbstractRngDataGenerator<LocalDate> {
    private final DataGenerator<? extends LocalDate> start;
    private final DataGenerator<? extends LocalDate> end;

    public RandomLocalDateGenerator(LocalDate start, LocalDate end) {
        this(constant(start), constant(end));
    }

    public RandomLocalDateGenerator(DataGenerator<? extends LocalDate> start, DataGenerator<? extends LocalDate> end) {
        this.start = start;
        this.end = end;

        registerChildGenerator(start);
        registerChildGenerator(end);

        setGeneratedClass(Instant.class);
    }


    @Override
    protected LocalDate generateInternal() {
        int days = Period.between(start.getValue(), end.getValue()).getDays();

        return start.getValue().plusDays(getRandom().nextInt(days));
    }
}
