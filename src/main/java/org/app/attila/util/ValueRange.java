package org.app.attila.util;

public class ValueRange {

    private int min_range;
    private int max_range;

    public ValueRange(int min_range, int max_range) {
        this.min_range = min_range;
        this.max_range = max_range;
    }

    public boolean valueInRange(int value) {
        return (value >= min_range && value <= max_range);
    }
}
