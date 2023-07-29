package cz.reservation.app.model;

import org.apache.commons.lang3.Range;

import java.time.LocalTime;

public class RangeOfLocalTimeFactory {
    public static Range<LocalTime> getNewRangeOfLocalTime(LocalTime startTime, LocalTime endTime) {
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("Range start time must be before range end time.");
        }
        return Range.between(startTime, endTime);
    }

}
