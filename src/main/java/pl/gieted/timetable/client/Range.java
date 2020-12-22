package pl.gieted.timetable.client;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class Range implements Iterable<Integer> {
    private final int lowerBound;
    private final int inclusiveUpperBound;

    public Range(int lowerBound, int inclusiveUpperBound) {
        this.lowerBound = lowerBound;
        this.inclusiveUpperBound = inclusiveUpperBound;
    }

    @Override
    public @NotNull Iterator<Integer> iterator() {
        return new Iterator<>() {
            private int current = lowerBound;

            @Override
            public boolean hasNext() {
                return current <= inclusiveUpperBound;
            }

            @Override
            public Integer next() {
                return current++;
            }
        };
    }
}
